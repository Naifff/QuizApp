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
				List.of(
						"JDK - набор инструментов для разработки",
						"JDK - библиотека классов Java",
						"JDK - компилятор Java",
						"JDK - язык программирования"
				),
				List.of("JDK - набор инструментов для разработки"),
				QuestionType.SINGLE_CHOICE
		));

		questionRepository.save(new Question(
				"Какие методы есть в Object?",
				List.of("equals()", "toString()", "hashCode()", "sort()", "clone()", "notify()"),
				List.of("equals()", "toString()", "hashCode()", "clone()", "notify()"),
				QuestionType.MULTIPLE_CHOICE
		));

		questionRepository.save(new Question(
				"Что выведет код?",
				null,
				List.of("321"),
				QuestionType.TEXT_INPUT
		));
	}
}
