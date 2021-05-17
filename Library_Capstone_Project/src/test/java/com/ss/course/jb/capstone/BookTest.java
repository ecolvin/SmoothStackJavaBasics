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
public class BookTest {
	@Test
	public void getBookIdTest() {
		Book b = new Book(3, "Test Title", 4, 7);
		assertEquals(3, b.getBookId());
	}
	
	@Test
	public void getTitleTest() {
		Book b = new Book(3, "Test Title", 4, 7);
		assertEquals("Test Title", b.getTitle());
	}
	
	@Test
	public void getAuthIdTest() {
		Book b = new Book(3, "Test Title", 4, 7);
		assertEquals(4, b.getAuthId());
	}
	
	@Test
	public void getPubIdTest() {
		Book b = new Book(3, "Test Title", 4, 7);
		assertEquals(7, b.getPubId());
	}
}
