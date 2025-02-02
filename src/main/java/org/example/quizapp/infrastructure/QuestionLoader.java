package org.example.quizapp.infrastructure;

import org.example.quizapp.domain.Question;
import org.example.quizapp.domain.QuestionType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionLoader {

	public List<Question> loadQuestions() {
		List<Question> questions = new ArrayList<>();

		try {
			ClassPathResource resource = new ClassPathResource("questions.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";"); // Разделяем по ";"
				if (parts.length < 4) continue;

				String text = parts[1]; // Текст вопроса
				List<String> options = Arrays.asList(parts[2].split(",")); // Варианты ответов
				List<String> correctAnswers = Arrays.asList(parts[3].split(",")); // Правильные ответы

				// Используем конструктор без id
				Question question = new Question(text, options, correctAnswers, QuestionType.MULTIPLE_CHOICE);
				questions.add(question);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return questions;
	}
}
