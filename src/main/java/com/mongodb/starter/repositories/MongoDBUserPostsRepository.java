package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.starter.models.UserPosts;

import jakarta.annotation.PostConstruct;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MongoDBUserPostsRepository implements UserPostsRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<UserPosts> userPostsCollection;

    public MongoDBUserPostsRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userPostsCollection = client.getDatabase("test").getCollection("userPosts", UserPosts.class);
    }

    @Override
    public UserPosts save(UserPosts userPosts) {
        userPostsCollection.insertOne(userPosts);
        return userPosts;
    }

    @Override
    public List<UserPosts> saveAll(List<UserPosts> userPostsList) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                userPostsList.forEach(userPosts -> userPostsCollection.insertOne(clientSession, userPosts));
                return userPostsList;
            }, txnOptions);
        }
    }

    @Override
    public List<UserPosts> findAll() {
        return userPostsCollection.find().into(new ArrayList<>());
    }

    @Override
    public UserPosts findOne(ObjectId userId) {
        return userPostsCollection.find(eq("userId", userId)).first();
    }

    @Override
    public long delete(ObjectId userId) {
        return userPostsCollection.deleteOne(eq("userId", userId)).getDeletedCount();
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> userPostsCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public UserPosts update(UserPosts userPosts) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
        return userPostsCollection.findOneAndReplace(eq("userId", userPosts.getUserId()), userPosts, options);
    }

    @Override
    public long update(List<Object> userPostsList) {
        List<Object> writes = userPostsList.stream()
                                                               .map(userPosts -> new ReplaceOneModel<>(
                                                                       eq("userId", userPosts.getUserId()), userPosts,
                                                                       new ReplaceOptions().upsert(true)))
                                                               .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> userPostsCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }
}
