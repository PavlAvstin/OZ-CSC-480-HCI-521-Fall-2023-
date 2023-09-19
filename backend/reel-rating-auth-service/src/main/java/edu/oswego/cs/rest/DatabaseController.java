package edu.oswego.cs.rest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
