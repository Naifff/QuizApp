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
	public String nextQuestion(HttpSession session, Model model) {
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		Integer questionIndex = (Integer) session.getAttribute("questionIndex");

		if (questions == null || questionIndex == null || questionIndex >= questions.size()) {
			return "redirect:/quiz/result";
		}

		Question question = questions.get(questionIndex);
		model.addAttribute("question", question);
		model.addAttribute("questionIndex", questionIndex + 1);
		model.addAttribute("totalQuestions", questions.size());

		session.setAttribute("questionIndex", questionIndex + 1); // Увеличиваем индекс
		return "quiz";
	}

	@PostMapping("/submit")
	public String submitAnswer(@RequestParam("questionId") Long questionId,
							   @RequestParam("answers") List<String> userAnswers,
							   HttpSession session) {

		List<Question> questions = (List<Question>) session.getAttribute("questions");
		Integer questionIndex = (Integer) session.getAttribute("questionIndex");
		int score = (int) session.getAttribute("score");

		if (questionIndex == null || questions == null || questionIndex == 0) {
			return "redirect:/quiz/start"; // Перезапуск теста, если данные потеряны
		}

		Question question = questions.get(questionIndex - 1); // Получаем текущий вопрос
		if (question.getCorrectAnswers().containsAll(userAnswers) && userAnswers.containsAll(question.getCorrectAnswers())) {
			score += 1; // Начисляем балл, если ответ полностью совпадает
		}

		session.setAttribute("score", score);

		return "redirect:/quiz/next";
	}

	@GetMapping("/result")
	public String showResult(HttpSession session, Model model) {
		int score = (int) session.getAttribute("score");
		model.addAttribute("score", score);
		return "result";
	}
}
