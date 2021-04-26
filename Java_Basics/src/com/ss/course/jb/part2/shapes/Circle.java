/**
 * 
 */
package com.ss.course.jb.part2.shapes;


/**
 * Circle child class of the Shape interface
 * @author Eric Colvin
 *
 */
public class Circle implements Shape{

	private int radius;
	
	/**
	 * Constructor for the Circle class
	 * @param r - The radius of the circle
	 */
	public Circle(int r) {
		radius = r;
	}
	
	/**
	 * Returns the area of the Circle
	 * @return - The area of the Circle
	 */
	public double calculateArea() {
		return Math.PI * radius * radius;
	}

	/**
	 * Prints out the radius of the Circle
	 * NOTE - I figured the main focus of this project was the interface/inheritance, not actually drawing the shapes, 
	 * so I decided to just print out the values of each variable to demonstrate that the interface and inheritance work 
	 * without having to deal with Graphics and drawing stuff.
	 */
	public void display() {
		System.out.println("Circle with a radius of " + radius);
	}
}
