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
import java.util.stream.Collectors;

@Service
public class QuestionLoader {

	public List<Question> loadQuestions() {
		List<Question> questions = new ArrayList<>();

		try {
			ClassPathResource resource = new ClassPathResource("questions.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";", -1); // Учитываем пустые значения

				if (parts.length < 4) continue;

				String text = parts[1].trim(); // Убираем пробелы
				List<String> options = Arrays.stream(parts[2].split(","))
						.map(String::trim) // Очищаем пробелы
						.collect(Collectors.toList());
				List<String> correctAnswers = Arrays.stream(parts[3].split(","))
						.map(String::trim)
						.collect(Collectors.toList());

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
