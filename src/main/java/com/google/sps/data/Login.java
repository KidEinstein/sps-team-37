package com.google.sps.data;

/** A Login object containing the emailAddress and redirect url */
public final class Login {
  
  private final String emailAddress;
  private final String url;

  public Login(String emailAddress, String url) {
    this.emailAddress = emailAddress;
    this.url = url;
  }

}
