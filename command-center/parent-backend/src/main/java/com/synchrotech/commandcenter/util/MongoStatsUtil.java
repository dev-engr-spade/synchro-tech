package com.synchrotech.commandcenter.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoStatsUtil {
    private final MongoClient mongoClient;

    @Autowired
    public MongoStatsUtil(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public long getDocumentCount(String dbName, String collectionName, String tenantId) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection.countDocuments(new Document("tenantId", tenantId));
    }

    public long getEstimatedSize(String dbName, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        Document stats = db.runCommand(new Document("collStats", collectionName));
        return stats.getLong("size");
    }
} 