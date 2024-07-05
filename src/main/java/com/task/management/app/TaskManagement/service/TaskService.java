package com.task.management.app.TaskManagement.service;

import com.task.management.app.TaskManagement.model.requests.AssignTask;
import com.task.management.app.TaskManagement.model.entries.Task;
import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.TaskRepository;
import com.task.management.app.TaskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(taskRepository.findAll(), HttpStatus.OK);
    }

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    @Transactional
    public ResponseEntity<?> createTask(Task task){
        if (taskRepository.findByTitle(task.getTitle()) != null){
            return new ResponseEntity<>("Task already present in DB", HttpStatus.CONFLICT);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(task.getDueDate(), dateTimeFormatter);
        LocalDate currentDate = LocalDate.now();
        if (localDate.isBefore(currentDate)){
            return new ResponseEntity<>("Please give date ahead of present date", HttpStatus.BAD_REQUEST);
        }
        Long epoch = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        task.setDueDate(String.valueOf(epoch));
        saveTask(task);
        User user = userRepository.findByName(task.getAssignedTo());
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if(user.getTasks().stream().anyMatch(t -> t.getTitle().equals(task.getTitle()))){
            return new ResponseEntity<>("Task already assigned to User", HttpStatus.CONFLICT);
        }
        Task savedTask = taskRepository.findByTitle(task.getTitle());
        user.getTasks().add(savedTask);
        userRepository.save(user);
        return new ResponseEntity<>(taskRepository.findByTitle(task.getTitle()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> assignTask(AssignTask task){
        List<Task> tasks = taskRepository.findAll();
        Optional<Task> filteredTask = tasks.stream().filter(x -> x.getId().toString().equals(task.getTaskId())).findFirst();

        // Filtering the user having this task already assigned
        Optional<User> userContainingTask = userRepository.findAll().stream()
                .filter(user -> user.getTasks().stream().anyMatch(t -> t.getId().toString().equals(task.getTaskId())))
                .findFirst();

        // Removing the task from other user before assigning it to other user
        if (filteredTask.isPresent() && userContainingTask.isPresent()){
            User user = userContainingTask.get();
            user.getTasks().removeIf(x -> x.getTitle().equals(filteredTask.get().getTitle()));
            userRepository.save(user);
        }

        // Task Assigned to the given User
        if (filteredTask.isPresent()){
            User user = userRepository.findByName(task.getAssignedTo());
            if (user.getTasks().stream().anyMatch(x -> x.getId().equals(filteredTask.get().getId()))){
                return new ResponseEntity<>("Task already assigned to User: "+ user.getName(), HttpStatus.CONFLICT);
            }
            Task taskDetails = taskRepository.findById(task.getTaskId().toString());
            taskDetails.setAssignedTo(user.getName());
            taskRepository.save(taskDetails);
            Task addTask = new Task();
            addTask.setId(taskDetails.getId());
            addTask.setTitle(taskDetails.getTitle());
            addTask.setDescription(taskDetails.getDescription());
            addTask.setDueDate(taskDetails.getDueDate());
            user.getTasks().add(addTask);
            userRepository.save(user);
            return new ResponseEntity<>(userRepository.findByName(task.getAssignedTo()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error in task assignment...", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> deleteTaskByName(String name){
        Task task = taskRepository.findByTitle(name);
        List<User> users = userRepository.findAll();
        IntStream.range(0, users.size()).forEach(user -> {
            List<Task> tasks = users.get(user).getTasks();
            IntStream.range(0, tasks.size()).forEach(t -> {
                if (tasks.get(t).getTitle().equals(name)){
                    tasks.remove(tasks.get(t));
                }
            });
            users.get(user).setTasks(tasks);
            userRepository.save(users.get(user));
        });
        taskRepository.delete(task);
        return new ResponseEntity<>("Task Deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAllTasks(){
        taskRepository.deleteAll();
        return new ResponseEntity<>("All tasks deleted...", HttpStatus.OK);
    }

}
