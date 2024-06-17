package com.task.management.app.TaskManagement.model.entries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "tasks")
public class Task {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    @JsonProperty("due_date")
    private String dueDate;
    @NonNull
    private String status;
    @JsonProperty("assigned_to")
    private String assignedTo;

}
