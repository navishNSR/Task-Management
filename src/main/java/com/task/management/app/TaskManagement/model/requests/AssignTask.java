package com.task.management.app.TaskManagement.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class AssignTask {

    @NonNull
    @JsonProperty("task_id")
    private String taskId;

    @NonNull
    @JsonProperty("assigned_to")
    private String assignedTo;

}
