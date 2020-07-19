
package com.google.sps.data;

/** A Journal object containing the text, mood value, song, artist, time, and email. */
public final class Journal {

  private final String textEntry;
  private final long moodValue;
  private final String songTitle;
  private final String artistName;
  private final long timestamp;
  private final String email;

  public Journal( String textEntry, long moodValue, String songTitle,  String artistName, long timestamp, String email ) {
    this.textEntry = textEntry;
    this.moodValue = moodValue;
    this.songTitle = songTitle;
    this.artistName = artistName;
    this.timestamp = timestamp;
    this.email = email;
  }
}