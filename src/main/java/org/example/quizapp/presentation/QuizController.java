package org.example.quizapp.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")  // Все маршруты начинаются с /quiz
public class QuizController {

	private final QuizService quizService;
	private int currentQuestionIndex = 0;
	private List<Question> questions;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/start")
	public String startQuiz(HttpSession session) {
		this.questions = quizService.getAllQuestions();  // Загружаем вопросы
		this.currentQuestionIndex = 0;  // Обнуляем индекс
		session.setAttribute("questionIndex", 0);
		session.setAttribute("score", 0); // Обнуляем счет
		return "redirect:/quiz/next";
	}



	@GetMapping("/next")
	public String nextQuestion(Model model) {
		if (questions == null || questions.isEmpty()) {
			return "redirect:/quiz/result";  // Если вопросов нет, переходим на результат
		}
		if (currentQuestionIndex >= questions.size()) {
			return "redirect:/quiz/result"; // Если вопросы кончились
		}

		model.addAttribute("question", questions.get(currentQuestionIndex));
		model.addAttribute("questionIndex", currentQuestionIndex + 1);
		model.addAttribute("totalQuestions", questions.size());

		currentQuestionIndex++;  // Переходим к следующему вопросу
		return "quiz";
	}

	@GetMapping("/result")
	public String showResult(Model model) {
		model.addAttribute("message", "Тест завершен!");
		return "result";
	}

	@PostMapping("/submit")
	public String submitQuiz(@RequestParam Map<Long, String> userAnswers, HttpSession session, Model model) {
		Map<Long, List<String>> correctAnswers = (Map<Long, List<String>>) session.getAttribute("correctAnswers");
		Map<Long, String> questionTypes = (Map<Long, String>) session.getAttribute("questionTypes");

		if (correctAnswers == null || userAnswers == null) {
			model.addAttribute("message", "Ошибка: ответы не были получены.");
			return "error"; // Вывод ошибки, если данные отсутствуют
		}

		int score = quizService.calculateScore(userAnswers, correctAnswers, questionTypes);
		session.setAttribute("score", score);

		return "redirect:/quiz/result";
	}



	@GetMapping("/")
	public String redirectToQuiz() {
		return "redirect:/quiz/start";
	}


}
