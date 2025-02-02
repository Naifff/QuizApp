package org.example.quizapp.presentation;

import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"currentIndex", "userAnswers"})
public class QuizController {
	private final QuizService quizService;

	@ModelAttribute("userAnswers")
	public Map<String, String[]> getUserAnswers() {
		return new HashMap<>();
	}

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@ModelAttribute("currentIndex")
	public Integer getCurrentIndex() {
		return 0;
	}

	@GetMapping("/")
	public String showQuiz(@ModelAttribute("currentIndex") Integer currentIndex, Model model) {
		List<Question> questions = quizService.getQuestions();

		if (currentIndex >= questions.size()) {
			return "redirect:/result";
		}

		model.addAttribute("question", questions.get(currentIndex));
		model.addAttribute("currentIndex", currentIndex);
		return "quiz";
	}

	@PostMapping("/submit")
	public String submitAnswer(@RequestParam Map<String, String> rawAnswers,
							   @ModelAttribute("userAnswers") Map<String, String[]> userAnswers,
							   @ModelAttribute("currentIndex") Integer currentIndex,
							   Model model) {
		List<Question> questions = quizService.getQuestions();

		if (currentIndex < questions.size()) {
			String questionId = String.valueOf(questions.get(currentIndex).getId());
			userAnswers.put(questionId, new String[]{rawAnswers.get("answer")});
		}

		// Выведем в лог, что сохраняем
		System.out.println("📌 Сохранён ответ: Вопрос ID " + currentIndex + " -> " + rawAnswers.get("answer"));

		model.addAttribute("userAnswers", userAnswers);
		model.addAttribute("currentIndex", currentIndex + 1);

		return "redirect:/";
	}

	@GetMapping("/result")
	public String showResults(@ModelAttribute("userAnswers") Map<String, String[]> userAnswers, Model model) {
		List<Question> questions = quizService.getQuestions();

		// Выведем ответы в лог
		System.out.println("📩 Ответы пользователя:");
		userAnswers.forEach((key, value) -> System.out.println("Вопрос ID: " + key + " | Ответ: " + Arrays.toString(value)));

		int score = quizService.calculateScore(userAnswers, questions);

		model.addAttribute("score", score);
		return "result";
	}



}
