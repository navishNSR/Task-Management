package com.task.management.app.TaskManagement.model.entries;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    @NotBlank(message = "Name is mandatory", groups = CreateValidationGroup.class)
    private String name;
    @NonNull
    @NotBlank(message = "Password is mandatory", groups = CreateValidationGroup.class)
    private String password;
    @NonNull
    @NotBlank(message = "Email is mandatory", groups = CreateValidationGroup.class)
    private String email;

    private List<String> role;

    @DBRef
    private List<Task> tasks = new ArrayList<>();

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("reports_to")
    private String reportsTo;

    private List<String> reporters = new ArrayList<>();

    public interface CreateValidationGroup {}

}
