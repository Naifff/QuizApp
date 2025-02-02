package org.example.quizapp.application.impl;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.example.quizapp.infrastructure.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

	private final QuestionRepository questionRepository;

	public QuizServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Override
	@Transactional(readOnly = true)  // Открываем сессию для Hibernate
	public List<Question> getAllQuestions() {
		List<Question> questions = questionRepository.findAll();

		// Принудительно загружаем связанные коллекции
		for (Question question : questions) {
			question.getOptions().size();  // Это инициализирует коллекцию options
			question.getCorrectAnswers().size();  // Это инициализирует correctAnswers
		}

		return questions;
	}

	@Override
	public int calculateScore(Map<Long, String> userAnswers, List<Question> questions) {
		int score = 0;

		for (Question question : questions) {
			String correctAnswer = String.join(",", question.getCorrectAnswers());
			String userAnswer = userAnswers.get(question.getId());

			if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
				score++;
			}
		}
		return score;
	}
}
