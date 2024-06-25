package net.engineeringdigest.journalApp.controller;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoTest {
    public static void main(String[] args) {
        String uri = "mongodb+srv://abc:abc@cluster0.auext0t.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("journaldb");
            MongoCollection<Document> collection = database.getCollection("testCollection");
            Document document = new Document("name", "testDocument")
                    .append("value", 1);
            collection.insertOne(document);
            System.out.println("Document inserted into the collection.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
