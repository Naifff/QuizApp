package org.example.quizapp.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@ElementCollection
	private List<String> options;

	private String correctAnswer;

	public Question() {
	}

	// Новый конструктор для всех типов вопросов
	public Question(String text, QuestionType type, List<String> options, String correctAnswer) {
		this.text = text;
		this.type = type;
		this.options = options;
		this.correctAnswer = correctAnswer;
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
}
