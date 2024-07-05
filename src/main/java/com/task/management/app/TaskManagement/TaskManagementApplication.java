package com.task.management.app.TaskManagement;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableRedisHttpSession
@EnableScheduling
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

	// Transaction Implementation
	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

}
