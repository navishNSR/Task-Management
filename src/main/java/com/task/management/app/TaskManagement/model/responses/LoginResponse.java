package com.task.management.app.TaskManagement.model.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String status;

    private String message;

    private String token;

}
