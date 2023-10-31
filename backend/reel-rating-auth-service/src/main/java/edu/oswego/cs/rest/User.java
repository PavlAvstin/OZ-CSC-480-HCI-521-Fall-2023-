package edu.oswego.cs.rest;

public class User {
  
  private String username;
  private String password;
  private String email;

  public User() {
  }

  public String setUsername(String username) {
    return this.username = username;
  }

  public String setPassword(String password) {
    return this.password = password;
  }

  public String setEmail(String email) {
    return this.email = email;
  }
  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
