/**
 * 
 */
package com.ss.course.jb.part4.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit testing for the Line.java class
 * @author Eric Colvin
 *
 */
public class LineTest {
	
	Line vertLine = new Line(0, 0, 0, 2);
	Line horizLine = new Line(0, 0, 2, 0);
	Line posLine = new Line(0, 0, 2, 2);
	Line negLine = new Line(0, 0, 2, -2);
	Line posLine2 = new Line(2, 2, 4, 4);
	Line negLine2 = new Line(2, -2, 4, -4);

	/**
	 * Test that getSlope returns the correct slopes for these lines
	 */
	@Test
	public void getSlopeTest() {
		assertEquals(0, horizLine.getSlope(), 0.0001);
		assertEquals(1, posLine.getSlope(), 0.0001);
		assertEquals(-1, negLine.getSlope(), 0.0001);
		assertEquals(1, posLine2.getSlope(), 0.0001);
		assertEquals(-1, negLine2.getSlope(), 0.0001);		
	}
	
	/**
	 * Test that getSlope throws an exception when the two x values are equal
	 */
	@Test(expected = ArithmeticException.class)
	public void getSlopeTestException() {
		vertLine.getSlope();
	}
	
	/**
	 * Test that the getDistance method return the correct values for all of the lines
	 */
	@Test
	public void getDistanceTest() {
		assertEquals(2, vertLine.getDistance(), 0.0001);
		assertEquals(2, horizLine.getDistance(), 0.0001);
		assertEquals(2.828427, posLine.getDistance(), 0.0001);
		assertEquals(2.828427, negLine.getDistance(), 0.0001);
		assertEquals(2.828427, posLine2.getDistance(), 0.0001);
		assertEquals(2.828427, negLine2.getDistance(), 0.0001);
	}
	
	/**
	 * Test that the parallelTo method correctly returns whether or not the lines are parallel
	 */
	@Test
	public void parallelToTest() {
		assertTrue(posLine.parallelTo(posLine2));
		assertTrue(negLine.parallelTo(negLine2));
		assertFalse(horizLine.parallelTo(posLine));
		assertFalse(horizLine.parallelTo(negLine));
		assertTrue(horizLine.parallelTo(horizLine));
	}
	
	/**
	 * Test that the parallelTo method throws an exception (from getSlope) when called by a vertical line
	 */
	@Test(expected = ArithmeticException.class)
	public void parallelToTestException1() {
		vertLine.parallelTo(horizLine);
	}
	
	/**
	 * Test that the parallelTo method throws an exception (from getSlope) when called on a vertical line
	 */
	@Test(expected = ArithmeticException.class)
	public void parallelToTestException2() {
		horizLine.parallelTo(vertLine);
	}
	
}
