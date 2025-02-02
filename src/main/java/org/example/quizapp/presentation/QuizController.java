package org.example.quizapp.presentation;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/")
	public String showQuiz(Model model) {
		List<Question> questions = quizService.getQuestions();

		System.out.println("=== Загружаемые вопросы ===");
		for (Question q : questions) {
			System.out.println("Вопрос: " + q.getText() + " | Тип: " + q.getQuestionType());
			System.out.println("Thymeleaf тип: " + q.getQuestionType().toString());
		}

		model.addAttribute("questions", questions);
		return "quiz";
	}




	@PostMapping("/submit")
	public String submitQuiz(@RequestParam Map<String, String> rawAnswers, Model model) {
		Map<String, String[]> answers = new HashMap<>();

		for (Map.Entry<String, String> entry : rawAnswers.entrySet()) {
			answers.put(entry.getKey(), new String[]{entry.getValue()});
		}

		List<Question> questions = quizService.getQuestions();
		int score = quizService.calculateScore(answers, questions);

		System.out.println("Ответы пользователя: " + answers);
		System.out.println("Итоговый балл: " + score);

		model.addAttribute("score", score);
		return "result";
	}


}
