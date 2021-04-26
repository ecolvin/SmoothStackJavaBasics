/**
 * 
 */
package com.ss.course.jb.part2.shapes;


/**
 * Triangle child class of the Shape interface
 * @author Eric Colvin
 *
 */
public class Triangle implements Shape{

	private int width;
	private int height;
	
	/**
	 * Constructor for the Triangle class
	 * @param w - The width of the Triangle
	 * @param h - The height of the Triangle
	 */
	public Triangle(int w, int h) {
		width = w;
		height = h;
	}
	
	/**
	 * Returns the area of the Triangle
	 * @return - The area of the Triangle
	 */
	public double calculateArea() {
		return (double)(width * height)/2.0;
	}

	/**
	 * Prints out the height and width of the triangle
	 * NOTE - I figured the main focus of this project was the interface/inheritance, not actually drawing the shapes, 
	 * so I decided to just print out the values of each variable to demonstrate that the interface and inheritance work 
	 * without having to deal with Graphics and drawing stuff.
	 */
	public void display() {
		System.out.println("Triangle with a width of " + width + " and a height of " + height);
	}

}
