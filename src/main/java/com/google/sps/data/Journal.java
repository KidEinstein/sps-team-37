
package com.google.sps.data;

/** A Journal object containing the mood value and time. */
public final class Journal {

  private final long moodValue;
  private final long timestamp;

  public Journal(long moodValue, long timestamp) {
    this.moodValue = moodValue;
    this.timestamp = timestamp;
  }
}