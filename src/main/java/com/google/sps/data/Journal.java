
package com.google.sps.data;

/** A Journal object containing the text, mood value, song, artist, and time. */
public final class Journal {

  private final String textEntry;
  private final long moodValue;
  private final String songTitle;
  private final String artistName;
  private final long timestamp;

  public Journal( String textEntry, long moodValue, String songTitle,  String artistName, long timestamp ) {
    this.textEntry = textEntry;
    this.moodValue = moodValue;
    this.songTitle = songTitle;
    this.artistName = artistName;
    this.timestamp = timestamp;
  }
}