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
public class AuthorTest {
	@Test
	public void getAuthorIdTest() {
		Author auth = new Author(3, "Test Name");
		assertEquals(3, auth.getAuthorId());
	}
	
	@Test
	public void getAuthorNameTest() {
		Author auth = new Author(3, "Test Name");
		assertEquals("Test Name", auth.getAuthorName());
	}
}
