/**
 * 
 */
package com.ss.course.jb.part2.shapes;


/**
 * Creates three different shape objects (one of each type) and calls their respective methods
 * @author Eric Colvin
 *
 */
public class Main {

	/**
	 * Create a Shape object of each type (Rectangle, Circle and Triangle) and call their calculateArea() and display() methods
	 * @param args - Command line arguments (unused)
	 */
	public static void main(String[] args) {
		Shape rect = new Rectangle(2, 4);
		Shape circ = new Circle(3);
		Shape tri = new Triangle(3, 3);
		System.out.println(rect.calculateArea());
		System.out.println(circ.calculateArea());
		System.out.println(tri.calculateArea());
		rect.display();
		circ.display();
		tri.display();
	}

}
