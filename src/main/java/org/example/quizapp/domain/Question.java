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

	public Question() {}

	public Question(String text, List<String> options, List<String> correctAnswers, QuestionType questionType) {
		this.text = text;
		this.options = options;
		this.correctAnswers = correctAnswers;
		this.questionType = questionType;
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

	public QuestionType getQuestionType() {
		return questionType;
	}

	// ✅ Добавляем метод isMultipleChoice()
	public boolean isMultipleChoice() {
		return questionType == QuestionType.MULTIPLE_CHOICE;
	}
}
