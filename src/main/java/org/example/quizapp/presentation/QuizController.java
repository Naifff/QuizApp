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
		session.invalidate(); // Очистка текущей сессии
//		session = request.getSession(true); // Создание новой сессии

		session.setAttribute("questionIndex", 0);
		session.setAttribute("score", 0);

		questions = quizService.getAllQuestions(); // Получаем все вопросы
		Map<Long, List<String>> correctAnswers = new HashMap<>();
		Map<Long, String> questionTypes = new HashMap<>();

		for (Question question : questions) {
			correctAnswers.put(question.getId(), question.getCorrectAnswers());
			questionTypes.put(question.getId(), question.getType().name());
		}

		session.setAttribute("correctAnswers", correctAnswers);
		session.setAttribute("questionTypes", questionTypes);

		return "redirect:/quiz/next";
	}







	@GetMapping("/next")
	public String nextQuestion(Model model, HttpSession session) {
		Integer index = (Integer) session.getAttribute("questionIndex");
		if (index == null) {
			index = 0;
		}

		if (questions == null || index >= questions.size()) {
			return "redirect:/quiz/result";
		}

		model.addAttribute("question", questions.get(index));
		model.addAttribute("questionIndex", index + 1);
		model.addAttribute("totalQuestions", questions.size());

		session.setAttribute("questionIndex", index + 1);  // Инкремент после показа
		return "quiz";
	}




	@GetMapping("/result")
	public String showResult(HttpSession session, Model model) {
		Integer score = (Integer) session.getAttribute("score");
		if (score == null) {
			score = 0;
		}

		model.addAttribute("score", score);
		System.out.println("Отображаемый балл: " + score);

		return "result";
	}


	@PostMapping("/submit")
	public String submitQuiz(@RequestParam Map<Long, String> userAnswers, HttpSession session, Model model) {
		// Логируем входные данные
		System.out.println("Ответы пользователя: " + userAnswers);

		// Получаем правильные ответы и типы вопросов
		Map<Long, List<String>> correctAnswers = (Map<Long, List<String>>) session.getAttribute("correctAnswers");
		Map<Long, String> questionTypes = (Map<Long, String>) session.getAttribute("questionTypes");

		if (correctAnswers == null || questionTypes == null) {
			System.err.println("Ошибка: нет данных о правильных ответах или типах вопросов!");
			return "redirect:/quiz/result";
		}

		System.out.println("Правильные ответы: " + correctAnswers);
		System.out.println("Типы вопросов: " + questionTypes);

		// Вычисляем баллы
		int score = quizService.calculateScore(userAnswers, correctAnswers, questionTypes);
		System.out.println("Подсчитанный балл: " + score);

		// Сохраняем балл в сессии
		session.setAttribute("score", score);

		return "redirect:/quiz/result";
	}





	@GetMapping("/")
	public String redirectToQuiz() {
		return "redirect:/quiz/start";
	}




}
