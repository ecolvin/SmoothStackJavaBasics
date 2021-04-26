/**
 * 
 */
package com.ss.course.jb.part2.shapes;


/**
 * An interface for various shape classes with a display method and an area method
 * @author Eric Colvin
 *
 */
public interface Shape {
	
	/**
	 * Calculate the area of the shape
	 * @return - The area of the shape
	 */
	public double calculateArea();
	
	/**
	 * Print out the values of each variable in the object
	 */
	public void display();
}
