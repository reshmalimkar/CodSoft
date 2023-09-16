package com.codSoft.quizapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class QuizQuestion {

	private String question;
	private List<String> option;
	private int correctOption;

	public QuizQuestion(String question2, List<String> options, int correctOption2) {
		this.question = question2;
		this.option = options;
		this.correctOption = correctOption2;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getOption() {
		return option;
	}

	public void setOption(List<String> option) {
		this.option = option;
	}

	public int getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}

}

class QuizTest {

	private int timeLimitInSecond;
	private int score;
	private List<QuizQuestion> quizQuestionList;
	private int incorrectAnsCount;
	private ExecutorService executorService;

	public QuizTest() {
		this.quizQuestionList = new ArrayList<>();
		this.timeLimitInSecond = 10;
		this.score = 0;
		this.incorrectAnsCount = 0;
		this.executorService = Executors.newSingleThreadExecutor();

	}

	public void addQuestions(String question, List<String> options, int correctOption) {
		QuizQuestion quizQuestion = new QuizQuestion(question, options, correctOption);
		quizQuestionList.add(quizQuestion);
		// System.out.println(quizQuestionList);
	}

	@SuppressWarnings("resource")
	public void startQuiz() {
		System.out.println("Welcome in Quiz");
		int avaliableQuestionNo = quizQuestionList.size();
		if (avaliableQuestionNo <= 0) {
			System.out.println("No Question Avaliable In Question List ");
		}
		for (int i = 0; i < quizQuestionList.size(); i++) {
			QuizQuestion quizQuestion = quizQuestionList.get(i);
			System.out.print("Question ");
			System.out.println(i + 1 + ". " + quizQuestion.getQuestion());
			List<String> option = quizQuestion.getOption();

			for (int j = 0; j < option.size(); j++) {
				System.out.println(j + 1 + ". " + option.get(j));

			}
			int correctOption = quizQuestion.getCorrectOption();

			Future<Integer> userInput = executorService.submit(() -> {
				System.out.println("Choose Correct Ans");
				return new Scanner(System.in).nextInt();
			});
			// Start the timer
			ScheduledExecutorService timerExecutors = Executors.newSingleThreadScheduledExecutor();
			Future<?> timerTask = timerExecutors.schedule(() -> {
				System.out.println("\nTime's up! The correct answer is option " + correctOption);
				userInput.cancel(true);
			}, timeLimitInSecond, TimeUnit.SECONDS);
			try {
				int userAns = userInput.get(timeLimitInSecond, TimeUnit.SECONDS);
				timerTask.cancel(true);
				if (userAns == correctOption) {
					score++;
				} else {
					System.out.println("Correct Ans Is : " + correctOption);
					incorrectAnsCount++;
				}
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				System.out.println("\nTime's up! The correct answer is option " + correctOption);
			}

		}
		System.out.println("Score : " + score);
		System.out.println("Incorrect Ans :" + incorrectAnsCount);
	}

}

public class Test {
	public static void main(String[] args) {
		QuizTest quizTest = new QuizTest();
		quizTest.addQuestions("Who invented Java Programming?", List.of("Guido van Rossum", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup"), 2);
		quizTest.addQuestions(" What is the extension of compiled java classes?", List.of(".txt", ".js", ".class", ".java"), 3);

		// startquiz
		quizTest.startQuiz();
	}
}
