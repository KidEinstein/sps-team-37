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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that stores journal entry data from the html form into Datastore*/
@WebServlet("/my-data-url")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Hello world!</h1>");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String textEntryString = request.getParameter("text-entry");
    String songEntryString = request.getParameter("song");
    String moodScaleString = request.getParameter("mood");

    // Ensure that form is filled out before saving to datastore
    if (textEntryString != null && !textEntryString.isEmpty()) {
      // Convert the mood input to an int.
      int moodScale = Integer.parseInt(moodScaleString);
  
      //Create journal entity with mood, journal entry, and song properties
      Entity journalEntity = new Entity("Journal");
      journalEntity.setProperty("text-entry", textEntryString);
      journalEntity.setProperty("song", songEntryString);
      journalEntity.setProperty("mood-scale", moodScale);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(journalEntity);
    }
    
    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
}
