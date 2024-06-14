package com.task.management.app.TaskManagement.model.entries;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "teams")
public class Team {

    @Id
    private ObjectId id;

    @JsonProperty("team_name")
    private String teamName;

    private String description;


    @DBRef
    private List<User> users = new ArrayList<>();



}
