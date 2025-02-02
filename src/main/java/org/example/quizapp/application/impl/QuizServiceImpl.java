package org.example.quizapp.application;

import org.example.quizapp.domain.Question;
import org.example.quizapp.infrastructure.QuestionLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

	private final List<Question> questions;

	public QuizServiceImpl(QuestionLoader questionLoader) {
		this.questions = questionLoader.loadQuestions();
	}

	@Override
	public List<Question> getAllQuestions() {
		return questions;
	}

	@Override
	public int calculateScore(Map<Long, String> userAnswers, List<Question> questions) {
		int score = 0;

		for (Question question : questions) {
			String correctAnswer = String.join(",", question.getCorrectAnswers()); // Объединяем, если несколько ответов
			String userAnswer = userAnswers.get(question.getId());

			if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
				score++;
			}
		}

		return score;
	}
}
