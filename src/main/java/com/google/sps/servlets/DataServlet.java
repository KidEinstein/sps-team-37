// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.data.Journal;
import com.google.sps.data.EmojiSelection;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/** Servlet that stores journal entry data from the html form into Datastore*/
@WebServlet("/my-data-url")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // If a user is not signed in, then don't retreive any entries
    UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) { 
      // No user is logged in
      return;
    }
    // Datastore query for journal entries ordered by time in ascending order. 
    Query journalQuery = new Query("Journal")
                  .addFilter("email", FilterOperator.EQUAL, currentUser.getEmail())
                  .addSort("timestamp", SortDirection.ASCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery journalResults = datastore.prepare(journalQuery);
    
    // An ArrayList to store Journal objects. 
    ArrayList<Journal> journalArrayList = new ArrayList<Journal>();

    // Loop through the queried results and create a Journal object to add to an ArrayList. 
    for (Entity journalEntity : journalResults.asIterable()) {
        String textEntry = (String) journalEntity.getProperty("text");
        long moodValue = (long) journalEntity.getProperty("mood");
        String songTitle = (String) journalEntity.getProperty("song");
        String artistName = (String) journalEntity.getProperty("artist");
        String emoji = (String) journalEntity.getProperty("emoji");
        long timestamp = (long) journalEntity.getProperty("timestamp");
        String email = (String) journalEntity.getProperty("email");

        Journal journal = new Journal(textEntry, moodValue, songTitle, artistName, emoji, timestamp, email);
        journalArrayList.add(journal);
    }

    // Convert the ArrayList of Journals into JSON format. 
    response.setContentType("application/json");
    Gson gson = new Gson();
    String journalJson = gson.toJson(journalArrayList);
    response.getWriter().println(journalJson);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Only allow to save if a user is logged in
    UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      // No user is logged in
      return;
    }

    // Get the input and time from the form.
    String textEntryString = request.getParameter("text");
    String moodScaleString = request.getParameter("mood");
    String songEntryString = request.getParameter("song");
    String artistEntryString = request.getParameter("artist"); 
    long timestamp = System.currentTimeMillis();
    String email = currentUser.getEmail();

    // Calls lyrics API
    Client client = ClientBuilder.newClient();
    Response lyricsResponce = client.target("https://api.lyrics.ovh/v1/" + artistEntryString + "/" + songEntryString)
      .request(MediaType.TEXT_PLAIN_TYPE)
      .get();
    JsonElement lyricsElement = new JsonParser().parse(lyricsResponce.readEntity(String.class));
    JsonObject  lyricsObject = lyricsElement.getAsJsonObject();
    String lyrics = lyricsObject.get("lyrics").getAsString().replace("\'","");

    // Ensure that form is filled out before saving to datastore
    if (textEntryString != null && !textEntryString.isEmpty()) {
      // Convert the mood input to an int.
      int moodScale = Integer.parseInt(moodScaleString);
      // Get emoji based on the moodScale
      String emojiString = EmojiSelection.getEmoji(moodScale);
  
      //Create journal entity with mood, journal entry, and song properties
      Entity journalEntity = new Entity("Journal");
      journalEntity.setProperty("text", textEntryString);
      journalEntity.setProperty("mood", moodScale);
      journalEntity.setProperty("song", songEntryString);
      journalEntity.setProperty("artist", artistEntryString);
      journalEntity.setProperty("emoji", emojiString);
      journalEntity.setProperty("timestamp", timestamp);
      journalEntity.setProperty("email", email);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(journalEntity);
    }
    
    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
