package com.task.management.app.TaskManagement.repository;

import com.task.management.app.TaskManagement.model.entries.User;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findByName(String name);

    User findById(ObjectId id);
}
