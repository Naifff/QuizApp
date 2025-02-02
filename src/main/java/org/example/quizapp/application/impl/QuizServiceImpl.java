package org.example.quizapp.application.impl;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.example.quizapp.infrastructure.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {
	private final QuestionRepository questionRepository;

	public QuizServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Override
	public List<Question> getAllQuestions() {
		return List.of();
	}

	@Override
	public int calculateScore(Map<Long, String> userAnswers, List<Question> questions) {
		return 0;
	}

	@Override
	public List<Question> getQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public int calculateScore(List<String> answers) {
		List<Question> questions = getQuestions();
		int score = 0;
		for (int i = 0; i < answers.size(); i++) {
			Question question = questions.get(i);
			if (question.getCorrectAnswers().contains(answers.get(i))) {
				score++;
			}
		}
		return score;
	}
}
