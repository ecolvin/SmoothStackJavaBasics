/**
 * 
 */
package com.ss.course.jb.capstone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Eric Colvin
 *
 */
public class BookCopyTest {
	@Test
	public void getBookIdTest() {
		BookCopy bc = new BookCopy(3, 4, 5);
		assertEquals(3, bc.getBookId());
	}
	
	@Test
	public void getBranchIdTest() {
		BookCopy bc = new BookCopy(3, 4, 5);
		assertEquals(4, bc.getBranchId());
	}
	
	@Test
	public void getNoOfCopiesTest() {
		BookCopy bc = new BookCopy(3, 4, 5);
		assertEquals(5, bc.getNoOfCopies());
	}
}
