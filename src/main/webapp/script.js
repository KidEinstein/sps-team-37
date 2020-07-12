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
 * Adds a random quote to the page.
 */
function addRandomQuote() {
    // Fetch a quote from the QuoteServlet
    fetch('/quotes').then(request => request.json()).then((quote_data) => {
        var quote = quote_data;
        // Add it to the page.
        const quoteContainer = document.getElementById('quote-container');
        quoteContainer.innerText = quote;
    });
}var request = new XMLHttpRequest();


function getLyrics() {
  document.getElementById('error-lyric').innerHTML = '';
  // Gets value from inputs and sets them
  var artist = document.getElementById('artist').value;
  var song = document.getElementById('song').value;
  var obj;
  // Calls lyrics.ovh API
  request.open('GET', 'https://api.lyrics.ovh/v1/' + artist+ '/' + song);
  // Returns lyrics in JSON format
  request.onreadystatechange = function () {
    if (this.readyState === 4) {
      var lyricsJSON = this.responseText;
      // API will return two key values, error (if not found), 
      // or lyrics (if found, but could possibly return empty string)
      obj = JSON.parse(lyricsJSON);
      if (obj.lyrics || obj.lyrics == '' || obj.lyrics == null) {
        console.log(obj.lyrics);
      } else {
        console.log(obj.error);
        document.getElementById('error-lyric').innerHTML = 'Could not find song. Check spelling, or choose another song.';
      }
    }
  };
  // Sends the request for lyrics
  request.send();
}
