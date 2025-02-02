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
		AtomicInteger score = new AtomicInteger();

		for (Question question : questions) {
			String questionId = String.valueOf(question.getId());
			String[] userResponse = userAnswers.get(questionId);

			System.out.println("🔎 Проверяем вопрос: " + question.getText());
			System.out.println("✅ Правильный ответ: " + question.getCorrectAnswers());
			System.out.println("❓ Ответ пользователя: " + Arrays.toString(userResponse));

			if (userResponse != null && Arrays.asList(userResponse).containsAll(question.getCorrectAnswers())) {
				score.incrementAndGet();
				System.out.println("🎯 Верный ответ! Балл добавлен.");
			} else {
				System.out.println("❌ Неверный ответ.");
			}
		}

		return score.get();
	}






}
