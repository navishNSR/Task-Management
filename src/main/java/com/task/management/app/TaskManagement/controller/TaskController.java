package com.task.management.app.TaskManagement.controller;

import com.task.management.app.TaskManagement.model.requests.AssignTask;
import com.task.management.app.TaskManagement.model.entries.Task;
import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.UserRepository;
import com.task.management.app.TaskManagement.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get-all-tasks")
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

//    @GetMapping("/get-task-{ID}")
//    public List<Task> getTaskById(){
//        return taskService.getAll();
//    }

    @GetMapping("/get-task/{name}")
    public ResponseEntity<?> getAllTasksOfAUser(@PathVariable String name) {
        try {
            User user = userRepository.findByName(name);
            return new ResponseEntity<>(user.getTasks(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in getting task of a user: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            return taskService.createTask(task);
        } catch (Exception e) {
            log.error("Error in add task: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/assign-task")
    public ResponseEntity<?> assignTaskToUser(@RequestBody AssignTask task) {
        try {
            return taskService.assignTask(task);
        } catch (Exception e) {
            log.error("Error in assigning task: " + e);
            return new ResponseEntity<>("Error in assigning task: " + e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-task/{name}")
    public ResponseEntity<?> deleteTaskByName(@PathVariable String name) {
        try {
            return taskService.deleteTaskByName(name);
        } catch (Exception e) {
            log.error("Error in deleting task: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete-all-tasks")
    public ResponseEntity<?> deleteAllTask() {
        try {
            return taskService.deleteAllTasks();
        } catch (Exception e) {
            log.error("Error in deleting all tasks: " + e);
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }
    }
}
