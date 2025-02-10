package com.hms;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class UserManagementModuleApplication {

	public static void main(String[] args) {
		 // Load environment variables using dotenv
        Dotenv dotenv = Dotenv.configure()
                              .directory("./")  // Ensure the correct directory
                              .filename(".env") // Specify the filename
                              .load();

        // Set system properties
        System.setProperty("MYSQL_USER", dotenv.get("MYSQL_USER"));
        System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
        System.setProperty("MYSQL_DB", dotenv.get("MYSQL_DB"));

        // Debugging: Print resolved properties
        System.out.println("MYSQL_USER: " + System.getProperty("MYSQL_USER"));
        System.out.println("MYSQL_PASSWORD: " + System.getProperty("MYSQL_PASSWORD"));
        System.out.println("MYSQL_DB: " + System.getProperty("MYSQL_DB"));
		SpringApplication.run(UserManagementModuleApplication.class, args);
	}

}
