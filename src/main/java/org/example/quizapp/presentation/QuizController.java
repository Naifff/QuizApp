package org.example.quizapp.presentation;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {
	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/")
	public String showQuiz(Model model) {
		List<Question> questions = quizService.getQuestions();

		if (questions.isEmpty()) {
			model.addAttribute("error", "Вопросы не загружены. Попробуйте позже.");
			return "error"; // Должен существовать шаблон error.html
		}

		model.addAttribute("questions", questions);
		return "quiz";
	}

	@PostMapping("/submit")
	public String submitQuiz(@RequestParam(value = "answer", required = false) List<String> answers, Model model) {
		if (answers == null || answers.isEmpty()) {
			model.addAttribute("error", "Вы не выбрали ни одного ответа.");
			return "quiz"; // Возвращаем пользователя на тест
		}

		int score = quizService.calculateScore(answers);
		model.addAttribute("score", score);
		return "result";
	}
}
