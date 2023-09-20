package edu.oswego.cs.rest;

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

  public void createUser(String username, String password, String sessionId, String dateTime) {
      var database = getUserCredentialsDatabase();
      var users = database.getCollection("users");
      var userDocument = new Document();
      userDocument.put("username", username);
      userDocument.put("password", password);
      userDocument.put("sessionId", sessionId);
      userDocument.put("dateTime", dateTime);
      users.insertOne(userDocument);
  }

  public boolean checkIfUserExists(String username) {
      MongoDatabase database = getUserCredentialsDatabase();
      MongoCollection<Document> users = database.getCollection("users");
      return null != users.find(Filters.eq("username", username)).first();
  }

  public void setUserSessionId(String username, String sessionId) {
      MongoDatabase database = getUserCredentialsDatabase();
      MongoCollection<Document> users = database.getCollection("users");
      Bson filter = Filters.eq("username", username);
      Bson updateOperation = Updates.set("sessionId", sessionId);
      users.updateOne(filter, updateOperation);
  }

  public String getUsername(String sessionId) {
      MongoDatabase database = getUserCredentialsDatabase();
      MongoCollection<Document> users = database.getCollection("users");
      Bson filter = Filters.eq("sessionId", sessionId);
      return users.find(filter).first().getString("username");
  }
}
