package org.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.quizapp.domain")
public class QuizAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}
}
