package edu.oswego.cs.rest;

public class User {
  
  private String username;
  private String password;

  public User() {
  }

  public String setUsername(String username) {
    return this.username = username;
  }

  public String setPassword(String password) {
    return this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
