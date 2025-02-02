package org.example.quizapp.infrastructure;

import jakarta.annotation.PostConstruct;
import org.example.quizapp.domain.Question;
import org.example.quizapp.domain.QuestionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

	private final QuestionRepository questionRepository;

	public DataLoader(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@PostConstruct
	public void loadQuestions() {
		questionRepository.save(new Question(
				"Что такое JDK, JVM, JRE?",
				QuestionType.SINGLE_CHOICE,
				List.of(
						"JDK - набор инструментов для разработки",
						"JDK - библиотека классов Java",
						"JDK - компилятор Java",
						"JDK - язык программирования"
				),
				"JDK - набор инструментов для разработки"
		));

		questionRepository.save(new Question(
				"Какие методы есть в Object?",
				QuestionType.MULTIPLE_CHOICE,
				List.of("equals()", "toString()", "hashCode()", "sort()", "clone()", "notify()"),
				"equals(),toString(),hashCode(),clone(),notify()" // несколько правильных ответов
		));

		questionRepository.save(new Question(
				"Что выведет код?",
				QuestionType.TEXT_INPUT,
				null,
				"321"
		));
	}
}
