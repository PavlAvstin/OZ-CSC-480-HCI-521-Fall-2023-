package edu.oswego.cs.rest;

import java.time.LocalDateTime;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

public class DatabaseController {
  
  String mongoDatabaseName = System.getenv("MONGO_CRED_DATABASE_NAME");
  String mongoURL = System.getenv("MONGO_CRED_URL");

  public MongoDatabase getUserCredentialsDatabase() {
      MongoClient mongoClient = MongoClients.create(mongoURL);
      return mongoClient.getDatabase(mongoDatabaseName);
  }

  public MongoCollection<Document> getUserCollection() {
    return getUserCredentialsDatabase().getCollection("users");
  }

  public void createUser(String username, String password, String sessionId, String dateTime) {
      var users = getUserCollection();
      var userDocument = new Document();
      userDocument.put("username", username);
      userDocument.put("password", password);
      userDocument.put("sessionId", sessionId);
      userDocument.put("dateTime", dateTime);
      userDocument.put("isValidSession", "true");
      invalidateAnySharedSessions(sessionId);
      users.insertOne(userDocument);
  }

  public boolean checkIfUserExists(String username) {
      MongoCollection<Document> users = getUserCollection();
      return null != users.find(Filters.eq("username", username)).first();
  }

  public void setUserSessionId(String username, String sessionId) {
      MongoCollection<Document> users = getUserCollection();
      Bson filter = Filters.eq("username", username);
      Bson updateOperation = Updates.set("sessionId", sessionId);
      invalidateAnySharedSessions(sessionId);
      users.updateOne(filter, updateOperation);
      users.updateOne(filter, Updates.set("isValidSession", "true"));
  }

  public void setUserDateTime(String username, String dateTime) {
      MongoCollection<Document> users = getUserCollection();
      Bson filter = Filters.eq("username", username);
      Bson updateOperation = Updates.set("dateTime", dateTime);
      users.updateOne(filter, updateOperation);
  }

  public boolean ensureSessionIsWithin24hours(String username) {
      MongoCollection<Document> users = getUserCollection();
      Bson filter = Filters.eq("username", username);
      String sessionStartString = users.find(filter).first().getString("dateTime");
      LocalDateTime loginTime = LocalDateTime.parse(sessionStartString);
      LocalDateTime currentTime = LocalDateTime.now();
      LocalDateTime endOfAllowedTime = loginTime.plusDays(1);
      return currentTime.compareTo(endOfAllowedTime) < 0;
  }

  public String getUsername(String sessionId) {
      MongoCollection<Document> users = getUserCollection();
      Bson sessionFilter = Filters.eq("sessionId", sessionId);
      Bson validSessionFilter = Filters.eq("isValidSession", "true");
      Bson filter = Filters.and(sessionFilter, validSessionFilter);
      return users.find(filter).first().getString("username");
  }

  public String getPassword(String username) {
      MongoCollection<Document> users = getUserCollection();
      Bson filter = Filters.eq("username", username);
      return users.find(filter).first().getString("password");
  }

  public void invalidateAnySharedSessions(String sessionId) {
    MongoCollection<Document> users = getUserCollection();
    Bson filter = Filters.eq("sessionId", sessionId);
    Bson updateOperation = Updates.set("isValidSession", "false");
    users.updateMany(filter, updateOperation);
  }

}
