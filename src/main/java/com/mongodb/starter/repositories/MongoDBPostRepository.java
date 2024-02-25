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
import com.mongodb.starter.models.Post;

import jakarta.annotation.PostConstruct;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MongoDBPostRepository implements PostRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<Post> postCollection;

    public MongoDBPostRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        postCollection = client.getDatabase("test").getCollection("posts", Post.class);
    }

    @Override
    public Post save(Post post) {
        post.setId(new ObjectId());
        postCollection.insertOne(post);
        return post;
    }

    @Override
    public List<Post> saveAll(List<Post> posts) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                posts.forEach(p -> p.setId(new ObjectId()));
                postCollection.insertMany(clientSession, posts);
                return posts;
            }, txnOptions);
        }
    }

    @Override
    public List<Post> findAll() {
        return postCollection.find().into(new ArrayList<>());
    }

    @Override
    public Post findOne(String id) {
        return postCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return postCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return postCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> postCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Post update(Post post) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return postCollection.findOneAndReplace(eq("_id", post.getId()), post, options);
    }

    @Override
    public long update(List<Post> posts) {
        List<ReplaceOneModel<Post>> writes = posts.stream()
                                                 .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                                                 .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> postCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }
}
