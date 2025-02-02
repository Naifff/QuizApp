package org.example.quizapp.application;

import org.example.quizapp.domain.Question;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса для управления викториной.
 */
public interface QuizService {

	/**
	 * Получает все доступные вопросы из файла.
	 *
	 * @return список вопросов
	 */
	List<Question> getQuestions();

	/**
	 * Вычисляет количество правильных ответов пользователя.
	 *
	 * @param answers список ответов пользователя
	 * @return количество правильных ответов
	 */
	int calculateScore(List<String> answers);

	/**
	 * Альтернативный метод расчета баллов с использованием мапы ответов.
	 *
	 * @param userAnswers карта ответов (ключ - ID вопроса, значение - ответ пользователя)
	 * @param questions список вопросов
	 * @return итоговый балл
	 */
	int calculateScore(Map<Long, String> userAnswers, List<Question> questions);
}
