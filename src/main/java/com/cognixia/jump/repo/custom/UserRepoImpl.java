package com.cognixia.jump.repo.custom;

import com.cognixia.jump.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.data.mongodb.core.query.Query;

public class UserRepoImpl implements UserRepoCustomUpdate{
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public User patch(String firstName, String lastName, String field, Object value) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName));
        query.addCriteria(Criteria.where("lastName").is(lastName));
        Update update = new Update();
        update.set(field, value);
        return mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), User.class);
    }
}
