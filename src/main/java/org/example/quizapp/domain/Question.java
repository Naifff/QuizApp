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

	@Enumerated(EnumType.STRING) // ✅ Добавьте эту аннотацию!
	private QuestionType questionType;

	// Геттер для id
	public Long getId() {
		return id;
	}

	// Геттер для correctAnswers
	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	// Сеттеры (если нужно)
	public void setId(Long id) {
		this.id = id;
	}

	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public Question(String text, List<String> correctAnswers, List<String> options, QuestionType questionType) {
		this.text = text;
		this.correctAnswers = correctAnswers;
		this.options = options;
		this.questionType = questionType;
	}
	public String getText() {
		return text;
	}
}
