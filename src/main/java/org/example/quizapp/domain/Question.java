package org.example.quizapp.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	@ElementCollection
	private List<String> options;

	@ElementCollection
	private List<String> correctAnswers;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	// Конструктор без параметров
	public Question() {}

	// Конструктор с параметрами
	public Question(Long id,String text, List<String> options, List<String> correctAnswers, QuestionType questionType) {
		this.id =id;
		this.text = text;
		this.options = options;
		this.correctAnswers = correctAnswers;
		this.questionType = questionType;
	}

	// Геттеры и сеттеры
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	// ✅ Добавь геттер для Thymeleaf
	public String getQuestionType() {
		return questionType.name(); // Преобразуем ENUM в String
	}
}
