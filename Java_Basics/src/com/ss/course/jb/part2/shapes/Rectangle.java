/**
 * 
 */
package com.ss.course.jb.part2.shapes;


/**
 * Rectangle child class of the Shape interface
 * @author Eric Colvin
 *
 */
public class Rectangle implements Shape{

	private int height;
	private int width;
	
	/**
	 * Constructor for the Rectangle class
	 * @param h - The height of the Rectangle
	 * @param w - The width of the Rectangle
	 */
	public Rectangle(int h, int w) {
		height = h;
		width = w;
	}
	
	/**
	 * Returns the area of the Rectangle
	 * @return - The area of the Rectangle
	 */
	public double calculateArea() {
		return height * width;
	}

	/**
	 * Prints out the height and width of the rectangle
	 * NOTE - I figured the main focus of this project was the interface/inheritance, not actually drawing the shapes, 
	 * so I decided to just print out the values of each variable to demonstrate that the interface and inheritance work 
	 * without having to deal with Graphics and drawing stuff.
	 */
	public void display() {
		System.out.println("Rectangle with a width of " + width + " and a height of " + height);		
	}
	
}
