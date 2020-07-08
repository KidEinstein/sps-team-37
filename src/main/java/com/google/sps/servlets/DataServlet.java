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
import com.google.gson.Gson;
import com.google.sps.data.Journal;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that stores journal entry data from the html form into Datastore*/
@WebServlet("/my-data-url")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // An ArrayList to store Journal objects. 
    ArrayList<Journal> journalArrayList = new ArrayList<Journal>();

    // Datastore query for journal entries ordered by time in ascending order. 
    Query journalQuery = new Query("Journal").addSort("timestamp", SortDirection.ASCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery journalResults = datastore.prepare(journalQuery);
    
    // Loop through the queried results and create a Journal object to add to an ArrayList. 
    for (Entity journalEntity : journalResults.asIterable()) {
        long moodValue = (long) journalEntity.getProperty("mood-scale");
        long timestamp = (long) journalEntity.getProperty("timestamp");
        Journal journal = new Journal(moodValue, timestamp);
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
    // Get the input and time from the form.
    String moodScaleString = request.getParameter("mood");
    long timestamp = System.currentTimeMillis();

    // Convert the mood input to an int.
    int moodScale = Integer.parseInt(moodScaleString);
 
    //Create journal entity with mood, journal entry, and song properties
    Entity journalEntity = new Entity("Journal");
    journalEntity.setProperty("mood-scale", moodScale);
    journalEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(journalEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
