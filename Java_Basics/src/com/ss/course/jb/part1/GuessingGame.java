/**
 * 
 */
package com.ss.course.jb.part1;

import java.util.Random;
import java.util.Scanner;

/**
 * User has 5 tries to guess a random number 1-100 and get within 10 of the correct number.
 * @author Eric Colvin
 *
 */
public class GuessingGame {

	/**
	 * Generates a random number and gives the user 5 tries to guess within 10 of it
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		
		int answer = rand.nextInt(100) + 1;
		
		System.out.println("I'm thinking of a number within 1 and 100.");
		System.out.println("You have 5 guesses to get within 10 of that number.");
		
		int guessed = 0;
		for(int count = 0; count < 5 && guessed == 0; count++) {
			System.out.println("Please guess a number between 1 and 100.");
			int guess = input.nextInt();
			
			if(guess <= answer + 10 && guess >= answer - 10) {
				guessed = 1;
				System.out.println("Correct! I was thinking of the number " + answer);
			}
			else {
				System.out.print("That was incorrect.");
				if(count != 4) {
					System.out.println(" Please try again.");
				} else {
					System.out.println(" The correct answer was " + answer + ".");
				}
			}
		}
		
	}

}
