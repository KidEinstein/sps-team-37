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

/**
 * Calls other functions to setup the page
 */
function setup() {
  addRandomQuote();
  addPastEntries();
}

/**
 * Adds a random quote to the page.
 */
function addRandomQuote() {
    // Fetch a quote from the QuoteServlet
    fetch("/quotes").then(request => request.json()).then((quote_data) => {
        var quote = quote_data;
        // Add it to the page.
        const quoteContainer = document.getElementById('quote-container');
        quoteContainer.innerText = quote;
    });
}

/**
 * Adds past journal entries to the page
 */
function addPastEntries() {
  fetch('/my-data-url').then(response => response.json()).then((pastEntries) => {
    // Convert message JSON array to HTML elements then add to page
    const pastEntriesListElement = document.getElementById('past-entries-container');
    for(var i = pastEntries.length-1; i >= 0; i--) {
      pastEntriesListElement.appendChild(createEntryElement(pastEntries[i]));
    }
  });
}

/** Creates a <div> element containing a journal entry. */
function createEntryElement(entry) {
  const entryElement = document.createElement('div');
  entryElement.setAttribute("class", "entry");

  const timestampElement = document.createElement('div');
  timestampElement.setAttribute("class", "entry-timestamp");
  timestampElement.innerText = getTimeAgo(entry.timestamp);

  const textElement = document.createElement('div');
  textElement.setAttribute("class", "entry-text");
  textElement.innerText = "Entry: " + entry.textEntry;

  const moodElement = document.createElement('div');
  moodElement.setAttribute("class", "entry-mood");
  moodElement.innerText = "Mood Rating: " + entry.moodValue;

  const emojiElement = document.createElement('div');
  emojiElement.setAttribute("class", "entry-emoji");
  emojiElement.innerText = "Mood Emoji: " + entry.emoji;

  const songElement = document.createElement('div');
  songElement.setAttribute("class", "entry-song");
  songElement.innerText = "Song: " + entry.songTitle + " by " + entry.artistName;

  entryElement.appendChild(timestampElement);
  entryElement.appendChild(textElement);
  entryElement.appendChild(moodElement);
  entryElement.appendChild(emojiElement);
  entryElement.appendChild(songElement);
  entryElement.appendChild(document.createElement('br'));
  
  return entryElement;
}

/** Returns a phrase describing the difference  
*   from the current time to when a entry was 
*   posted, in milliseconds, as a string.
*/
function getTimeAgo(timeInMilli) {
  const diffInMilli = Math.abs((new Date()) - (new Date(timeInMilli)));
  if (diffInMilli < 60000) {
    return "Less than 1 minute ago";
  }
  const minutes = diffInMilli / 60000;
  if (minutes < 60) {
    return Math.floor(minutes) + " minutes ago";
  }
  const hours = minutes / 60;
  if (hours < 24) {
    return Math.floor(hours) + " hours ago";
  }
  const days = hours / 24;
  if (days < 31) {
    return Math.floor(days) + " days ago";
  }
  const months = days / 30;
  if (months < 12) {
    return Math.floor(months) + " months ago";
  }
  return Math.floor(months / 12) + " years ago";
}
