package org.example.quizapp.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	private List<String> correctAnswers; // <-- Должно быть объявлено


	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@ElementCollection
	private List<String> options;

	private String correctAnswer;

	public Question() {
	}

	// Новый конструктор для всех типов вопросов
	public Question(Long id, String text, List<String> options, List<String> correctAnswers, QuestionType type) {
		this.id = id;
		this.text = text;
		this.options = options;
		this.correctAnswers = correctAnswers;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public QuestionType getType() {
		return type;
	}

	public List<String> getOptions() {
		return options;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	// Геттер для correctAnswers
	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	// Сеттер для correctAnswers
	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

}
