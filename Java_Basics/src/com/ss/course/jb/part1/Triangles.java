/**
 * 
 */
package com.ss.course.jb.part1;

/**
 * Print 4 different types of triangles
 * @author Eric Colvin
 *
 */
public class Triangles {

	/**
	 * Calls each of the 4 triangle methods with 4 rows 
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Triangles tri = new Triangles();
		
		tri.increasing(4);
		System.out.println();
		System.out.println();
		
		tri.decreasing(4);
		System.out.println();
		System.out.println();
		
		tri.upArrow(4);
		System.out.println();
		System.out.println();
		
		tri.downArrow(4);
	}
	
	/**
	 * Print a right triangle with an increasing # of stars in each row	
	 * @param rows - # of rows of stars to print
	 */
	public void increasing(Integer rows) {
		for(int i = 1; i <= rows; i++)
		{
			for(int j = 0; j < i; j++) {
				System.out.print('*');
			}
			System.out.println();
		}
		System.out.println("----------");
	}
	
	/**
	 * Print a right triangle with a decreasing # of stars in each row	
	 * @param rows - # of rows of stars to print
	 */
	public void decreasing(Integer rows) {
		System.out.println("----------");
		for(int i = rows; i > 0; i--)
		{
			for(int j = 0; j < i; j++) {
				System.out.print('*');
			}
			System.out.println();
		}
	}

	/**
	 * Print an isosceles triangle with an increasing # of stars in each row	
	 * @param rows - # of rows of stars to print
	 */
	public void upArrow(Integer rows) {
		for(int i = 1; i <= rows; i++) {
			int numSpaces = (10-(2*(i-1)))/2;
			for(int s = 0; s < numSpaces; s++) { 
				System.out.print(' ');
			}
			for(int j = 0; j < (2*(i-1))+1; j++) {
				System.out.print('*');
			}
			System.out.println();
		}
		System.out.println("-----------");
	}

	/**
	 * Print an isosceles triangle with a decreasing # of stars in each row	
	 * @param rows - # of rows of stars to print
	 */
	public void downArrow(Integer rows) {
		System.out.println("-----------");
		for(int i = rows; i > 0; i--) {
			int numSpaces = (10-(2*(i-1)))/2;
			for(int s = 0; s < numSpaces; s++) {
				System.out.print(' ');
			}
			for(int j = 0; j < (2*(i-1))+1; j++) {
				System.out.print('*');
			}
			System.out.println();
		}
	}


}
