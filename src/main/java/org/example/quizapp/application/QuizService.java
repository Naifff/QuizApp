package org.example.quizapp.application;

import org.example.quizapp.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuizService {
	List<Question> getQuestions();
	List<Question> getAllQuestions();  // Добавляем метод

	/*int calculateScore(
			Map<Long, String> singleAnswers,
			Map<Long, List<String>> multipleAnswers,
			Map<Long, String> textAnswers
	);*/
	int calculateScore(Map<Long, String> userAnswers, Map<Long, List<String>> correctAnswers, Map<Long, String> questionTypes);

}
