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
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.starter.models.UserFriends;

import jakarta.annotation.PostConstruct;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MongoDBUserFriendsRepository implements UserFriendsRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<UserFriends> userFriendsCollection;

    public MongoDBUserFriendsRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userFriendsCollection = client.getDatabase("test").getCollection("userFriends", UserFriends.class);
    }

    @Override
    public UserFriends save(UserFriends userFriends) {
        userFriendsCollection.insertOne(userFriends);
        return userFriends;
    }

    @Override
    public List<UserFriends> saveAll(List<UserFriends> userFriendsList) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                userFriendsList.forEach(userFriends -> userFriendsCollection.insertOne(clientSession, userFriends));
                return userFriendsList;
            }, txnOptions);
        }
    }

    @Override
    public List<UserFriends> findAll() {
        return userFriendsCollection.find().into(new ArrayList<>());
    }

    @Override
    public UserFriends findOne(ObjectId userId) {
        return userFriendsCollection.find(eq("userId", userId)).first();
    }

    @Override
    public long delete(ObjectId userId) {
        return userFriendsCollection.deleteOne(eq("userId", userId)).getDeletedCount();
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> userFriendsCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public UserFriends update(UserFriends userFriends) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
        return userFriendsCollection.findOneAndReplace(eq("userId", userFriends.getUserId()), userFriends, options);
    }

    @Override
    public long update(List<UserFriends> userFriendsList) {
        List<Object> writes = userFriendsList.stream()
                                                                 .map(userFriends -> new ReplaceOneModel<>(
                                                                         eq("userId", userFriends.getUserId()),
                                                                         userFriends, new ReplaceOptions().upsert(true)))
                                                                 .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> userFriendsCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }
}
