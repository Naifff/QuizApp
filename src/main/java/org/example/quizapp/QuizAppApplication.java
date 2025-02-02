package org.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.example.quizapp.infrastructure")
public class QuizAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}
}
