package org.example.quizapp.application;

import org.example.quizapp.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuizService {
	List<Question> getAllQuestions();
	int calculateScore(Map<Long, String> userAnswers, List<Question> questions);
}
