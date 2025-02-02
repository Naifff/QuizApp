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
	private QuestionType type;

	public Question() {}

	public Question(String text, List<String> options, List<String> correctAnswers, QuestionType type) {
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

	public List<String> getOptions() {
		return options;
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public QuestionType getType() {
		return type;
	}

	public boolean isMultipleChoice() {
		return correctAnswers.size() > 1;
	}
}
