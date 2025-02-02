package org.example.quizapp.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.quizapp.application.QuizService;
import org.example.quizapp.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/start")
	public String startQuiz(HttpSession session) {
		List<Question> questions = quizService.getAllQuestions();
		session.setAttribute("questions", questions);
		session.setAttribute("questionIndex", 0);
		session.setAttribute("score", 0);
		return "redirect:/quiz/next";
	}


	@GetMapping("/next")
	public String nextQuestion(Model model, HttpSession session) {
		List<Question> questions = (List<Question>) session.getAttribute("questions");

		if (questions == null) {
			questions = quizService.getAllQuestions(); // Загружаем вопросы
			session.setAttribute("questions", questions);
		}

		int currentQuestionIndex = (int) session.getAttribute("questionIndex");

		if (currentQuestionIndex >= questions.size()) {
			return "redirect:/quiz/result"; // Если вопросов больше нет, переходим к результату
		}

		Question question = questions.get(currentQuestionIndex);

		model.addAttribute("question", question);
		model.addAttribute("questionIndex", currentQuestionIndex + 1);
		model.addAttribute("totalQuestions", questions.size());

		return "quiz";
	}


	@PostMapping("/submit")
	public String submitQuiz(@RequestParam Map<String, String> userAnswers, HttpSession session, Model model) {
		int currentQuestionIndex = (int) session.getAttribute("questionIndex"); // Текущий вопрос
		List<Question> questions = (List<Question>) session.getAttribute("questions"); // Получаем список вопросов

		if (questions == null || currentQuestionIndex >= questions.size()) {
			return "redirect:/quiz/result"; // Если вопросы закончились → результат
		}

		// Получаем правильные ответы из текущего вопроса
		Question currentQuestion = questions.get(currentQuestionIndex);
		List<String> correctAnswers = currentQuestion.getCorrectAnswers();

		// Получаем ответ пользователя
		String userAnswer = userAnswers.get("answer"); // "answer" - name в HTML-форме

		// Сравниваем ответы
		int score = (int) session.getAttribute("score");
		if (correctAnswers.contains(userAnswer)) {
			score++; // Если ответ верный → увеличиваем баллы
		}
		session.setAttribute("score", score); // Обновляем баллы

		// Переходим к следующему вопросу
		session.setAttribute("questionIndex", currentQuestionIndex + 1);

		return "redirect:/quiz/next"; // Переход к следующему вопросу
	}


	@GetMapping("/result")
	public String showResult(HttpSession session, Model model) {
		int score = (int) session.getAttribute("score");
		model.addAttribute("score", score);
		return "result";
	}
}
