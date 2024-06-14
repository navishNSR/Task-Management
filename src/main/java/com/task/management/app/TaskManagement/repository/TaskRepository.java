package com.task.management.app.TaskManagement.repository;

import com.task.management.app.TaskManagement.model.entries.Task;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, Long> {

    Task findById(String id);

    Task findByTitle(String title);

}
