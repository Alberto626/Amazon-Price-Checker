package com.example.AmazonTracker;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling

public class AmazonTrackerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AmazonTrackerApplication.class, args);
	}

}
//TODO add .env, spring secret managements