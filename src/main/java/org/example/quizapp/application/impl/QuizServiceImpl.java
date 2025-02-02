package org.example.quizapp.application.impl;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.example.quizapp.infrastructure.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

	private final QuestionRepository questionRepository;

	public QuizServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Question> getQuestions() {
		List<Question> questions = questionRepository.findAll();
		questions.forEach(q -> q.getOptions().size()); // Принудительно загружаем варианты ответа
		return questions;
	}

	@Override
	public int calculateScore(Map<Long, String> userAnswers,
							  Map<Long, List<String>> correctAnswers,
							  Map<Long, String> questionTypes) {
		int score = 0;

		for (Long questionId : userAnswers.keySet()) {
			String userAnswer = userAnswers.get(questionId);
			List<String> correct = correctAnswers.get(questionId);
			String questionType = questionTypes.get(questionId);

			if (correct == null) {
				System.err.println("Ошибка: нет правильного ответа для вопроса " + questionId);
				continue;
			}

			if (questionType == null) {
				System.err.println("Ошибка: нет типа вопроса для " + questionId);
				continue;
			}

			System.out.println("Проверка вопроса " + questionId + ": пользовательский ответ = " + userAnswer + ", правильный = " + correct);

			if (questionType.equalsIgnoreCase("SINGLE") && correct.contains(userAnswer)) {
				score++;
			} else if (questionType.equalsIgnoreCase("MULTIPLE") && userAnswer != null) {
				List<String> userAnswersList = Arrays.asList(userAnswer.split(","));
				if (userAnswersList.containsAll(correct) && correct.containsAll(userAnswersList)) {
					score++;
				}
			}
		}

		System.out.println("Финальный балл: " + score);
		return score;
	}




	@Transactional  // Убедимся, что сессия активна
	@Override
	public List<Question> getAllQuestions() {
		List<Question> questions = questionRepository.findAll();
		// Явно загружаем options, чтобы избежать LazyInitializationException
		questions.forEach(q -> q.getOptions().size());
		return questions;
	}

}
