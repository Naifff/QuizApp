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
		model.addAttribute("questions", questions);
		return "quiz";
	}

	@PostMapping("/submit")
	public String submitQuiz(@RequestParam("answer") List<String> answers, Model model) {
		int score = quizService.calculateScore(answers);
		model.addAttribute("score", score);
		return "result";
	}
}
