package org.example.quizapp.application.impl;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.example.quizapp.domain.QuestionType;
import org.example.quizapp.infrastructure.QuestionLoader;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiceImpl implements QuizService {
	private final QuestionLoader questionLoader;

	public QuizServiceImpl(QuestionLoader questionLoader) {
		this.questionLoader = questionLoader;
	}

	@Override
	public List<Question> getQuestions() {
		List<Question> questions = questionLoader.loadQuestions();

		if (questions.isEmpty()) {
			System.out.println("❌ Ошибка: вопросы не загружены!");
		} else {
			System.out.println("✅ Загружено вопросов: " + questions.size());
		}

		return questions;
	}

	@Override
	public int calculateScore(List<String> answers) {
		List<Question> questions = getQuestions();
		int score = 0;

		if (answers == null || answers.isEmpty()) {
			System.out.println("⚠️ Пользователь не выбрал ответы!");
			return 0;
		}

		if (questions.size() != answers.size()) {
			System.out.println("⚠️ Несовпадение количества вопросов и ответов! Проверьте данные.");
		}

		for (int i = 0; i < Math.min(answers.size(), questions.size()); i++) {
			Question question = questions.get(i);
			String userAnswer = answers.get(i).trim();

			if (question.getCorrectAnswers().contains(userAnswer)) {
				score++;
			}
		}

		System.out.println("✅ Итоговый счет: " + score);
		return score;
	}

	@Override
	public int calculateScore(Map<String, String[]> userAnswers, List<Question> questions) {
		int score = 0;

		for (Question question : questions) {
			String key = "answer_" + question.getId();
			String[] userResponse = userAnswers.get(key);

			if (userResponse != null) {
				List<String> userChoices = Arrays.asList(userResponse);
				List<String> correctAnswers = question.getCorrectAnswers();

				System.out.println("❓ Вопрос: " + question.getText());
				System.out.println("✅ Правильные ответы: " + correctAnswers);
				System.out.println("🔍 Ответы пользователя: " + userChoices);

				// ✅ Определяем, является ли questionType строкой


String questionType =  question.getQuestionType();
				// ✅ Проверяем MULTIPLE_CHOICE
				if (questionType .equals("MULTIPLE_CHOICE")) {
					if (userChoices.containsAll(correctAnswers) && correctAnswers.containsAll(userChoices)) {
						score++;
						System.out.println("🎯 Верный ответ! +1 балл");
					} else {
						System.out.println("❌ Ошибка. Ответы не совпадают.");
					}
				}
				// ✅ Для SINGLE_CHOICE и TEXT просто сравниваем строки
				else {
					if (correctAnswers.contains(userChoices.get(0))) {
						score++;
						System.out.println("🎯 Верный ответ! +1 балл");
					} else {
						System.out.println("❌ Ошибка. Неверный ответ.");
					}
				}
			}
		}

		System.out.println("🔢 Итоговый балл: " + score);
		return score;
	}





}
