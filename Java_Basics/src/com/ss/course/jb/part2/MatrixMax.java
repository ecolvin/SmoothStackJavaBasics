/**
 * 
 */
package com.ss.course.jb.part2;

import java.util.Random;

/**
 * Find the value and position of the largest number in a 2D array
 * @author Eric Colvin
 *
 */
public class MatrixMax {

	/**
	 * Randomly generates a 4x4 array of integers less than 100 and then finds the value and position of the largest number in the array
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		int [][] matrix = new int[4][4];
		
		Random rand = new Random();
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {				
				matrix[i][j] = rand.nextInt(100);
			}
		}
		
		System.out.println("Array:");
		
		int max = 0;
		int max_i = 0;
		int max_j = 0;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
				if(matrix[i][j] > matrix[max_i][max_j]) {
					max = matrix[i][j];
					max_i = i;
					max_j = j;   
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("The largest number in the array is " + max);
		System.out.println("It is found at position (" + max_i + "," + max_j + ")");
	}
}
