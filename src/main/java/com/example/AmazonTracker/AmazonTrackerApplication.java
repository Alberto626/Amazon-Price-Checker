package com.example.AmazonTracker;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class AmazonTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonTrackerApplication.class, args);
	}

}
//TODO change application properties to drop the table and create a new one to add new changes to database structure
