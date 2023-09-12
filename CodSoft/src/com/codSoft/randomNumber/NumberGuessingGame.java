package com.codSoft.randomNumber;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		int min = 1;
		int max = 100;
		int maxAttempts = 10;
		int totalAttempts = 0;
		boolean continueGame = true;
		while (continueGame) {
			int numberofguess = 0;
			int randomNumber = random.nextInt(max - min + 1) + min;
			System.out.println("welcome to the Number Guessing Game!");
			System.out.println("the number is in between " + min + " and " + max + ".");
			System.out.println("You have " + maxAttempts + " attempts to guess it.");
			while (numberofguess < maxAttempts) {
				System.out.println("guess " + (numberofguess + 1) + ": ");

				int userguess = sc.nextInt();
				numberofguess++;
				totalAttempts++;
				if (userguess == randomNumber) {
					System.out.println("congratulation...you guess the correct number  :" + randomNumber);
					System.out.println("number of guess is :" + numberofguess);
					break;
				} else if (userguess < randomNumber) {
					System.out.println("sorry,your guess is too low");
				} else {
					System.out.println("sorry,your guess is too high");
				}

			}
			if (numberofguess == maxAttempts) {
				System.out.println("Sorry, you've used all your attempts. The correct number was:  " + randomNumber);
			}
			System.out.print("Do you want to play again? (yes/no): ");
			String continueGameResponse = sc.next().toLowerCase();
			continueGame = continueGameResponse.equals("yes");

		}
		System.out.println("Thanks for playing!");
		System.out.println("Total attempts: " + totalAttempts);
		sc.close();

	}

}
