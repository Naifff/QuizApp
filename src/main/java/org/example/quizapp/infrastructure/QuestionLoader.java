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
				System.out.println("Загружаем строку: " + line); // Проверка загрузки строк
				String[] parts = line.split(";", -1);

				if (parts.length < 5) {
					System.out.println("⚠️ Ошибка парсинга строки (недостаточно данных): " + line);
					continue;
				}
				Long id = Long.valueOf(parts[0]);
				String text = parts[1].trim();
				List<String> options = Arrays.stream(parts[2].split(","))
						.map(String::trim)
						.collect(Collectors.toList());
				List<String> correctAnswers = Arrays.stream(parts[3].split(","))
						.map(String::trim)
						.collect(Collectors.toList());
				String typeStr = parts[4].trim().toUpperCase();

				QuestionType questionType;
				switch (typeStr) {
					case "TEXT":
						questionType = QuestionType.TEXT;
						break;
					case "SINGLE_CHOICE":
						questionType = QuestionType.SINGLE_CHOICE;
						break;
					case "MULTIPLE_CHOICE":
						questionType = QuestionType.MULTIPLE_CHOICE;
						break;
					default:
						System.out.println("⚠️ Неизвестный тип вопроса: " + typeStr);
						continue;
				}

				System.out.println("✅ Загружен вопрос: " + text + " | Тип: " + questionType);

				Question question = new Question(id,text, options, correctAnswers, questionType);
				questions.add(question);
			}


			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return questions;
	}
}
