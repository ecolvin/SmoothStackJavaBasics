/**
 * 
 */
package com.ss.course.jb.part2;

/**
 * Print out the sum of all numbers passed to the command line
 * @author Eric Colvin
 *
 */
public class CommandSum {

	/**
	 * Gets and prints the sum of all numbers in args
	 * @param args - List of numbers to add together
	 */
	public static void main(String[] args) {
		Integer sum = 0;
		for(int i = 0; i < args.length; i++) {
			try	{
				sum += Integer.parseInt(args[i]);
			}
			catch(Exception e) {
				System.out.println("\"" + args[i] + "\" is not an integer.");
				System.out.println("It will not be included in the sum.");
			}
		}
		System.out.println("The sum is " + sum + ".");
	}

}
