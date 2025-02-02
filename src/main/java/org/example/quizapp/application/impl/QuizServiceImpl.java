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

		for (Map.Entry<Long, String> entry : userAnswers.entrySet()) {
			Long questionId = entry.getKey();
			String userAnswer = entry.getValue();

			if (correctAnswers.containsKey(questionId)) {
				List<String> correct = correctAnswers.get(questionId);

				// Проверяем, есть ли ответ пользователя среди правильных
				if (correct.contains(userAnswer)) {
					score++;
				}
			}
		}

		System.out.println("Final score: " + score);  // Отладочный вывод
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
