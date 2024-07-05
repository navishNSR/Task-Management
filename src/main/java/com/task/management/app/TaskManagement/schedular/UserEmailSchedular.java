package com.task.management.app.TaskManagement.schedular;

import com.task.management.app.TaskManagement.model.entries.Task;
import com.task.management.app.TaskManagement.model.entries.User;
import com.task.management.app.TaskManagement.repository.TaskRepository;
import com.task.management.app.TaskManagement.service.EmailService;
import com.task.management.app.TaskManagement.service.UserService;
import com.task.management.app.TaskManagement.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class UserEmailSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DateTimeUtils dateTimeUtils;

    @Scheduled(cron = "0/5 * * * * ?")
    public void checkDueDateAndSendMail(){
        List<User> allUsers = userService.getAllUsers();
        List<Task> allTasks = taskRepository.findAll();

        allTasks.forEach(task -> {
            if (Long.parseLong(task.getDueDate()) >= Instant.now().toEpochMilli() &&
                    Long.parseLong(task.getDueDate()) <= Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli() &&
                    !task.getStatus().equalsIgnoreCase("completed")){

                String username = task.getAssignedTo();
                Optional<User> user = allUsers.stream().filter(u -> u.getName().equals(username)).findFirst();
                if (user.isPresent()){
                    String subject = "Mail regarding Assigned Task Completion";
                    String body = "Reminder !!! \n\n"+
                            "Hi "+ user.get().getName() +",\n\n"+
                            "Due date for your task is \n" +
                            DateTimeUtils.convertEpocToDaysAndHours(Long.parseLong(task.getDueDate())) +"\n\n"+
                            "and your task status is \""+ task.getStatus() +"\"\n\n"+
                            "Please look into it...\n\n"+
                            "Regards\n\n"+
                            "Task Management App";
                    emailService.sendMail(user.get().getEmail(), subject, body);
                }
            } else if (Long.parseLong(task.getDueDate()) <= Instant.now().toEpochMilli() &&
                    !task.getStatus().equalsIgnoreCase("completed")){

                String username = task.getAssignedTo();
                Optional<User> user = allUsers.stream().filter(u -> u.getName().equals(username)).findFirst();
                if (user.isPresent()){
                    String subject = "Mail regarding Assigned Task Completion";
                    String body = "Alert !!! \n\n"+
                            "Hi "+ user.get().getName() +",\n\n"+
                            "Due date for your task was \n" +
                            dateTimeUtils.convertEpocToDaysAndHours(Long.parseLong(task.getDueDate())) +"\n\n"+
                            "which is expired and your task status is still \""+ task.getStatus() +".\"\n\n"+
                            "Please look into it...\n\n"+
                            "Regards\n\n"+
                            "Task Management App";
                    emailService.sendMail(user.get().getEmail(), subject, body);
                }
            }
        });

    }
}
