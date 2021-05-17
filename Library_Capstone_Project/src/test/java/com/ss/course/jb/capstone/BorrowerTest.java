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
public class BorrowerTest {
	@Test
	public void getCardNoTest() {
		Borrower borr = new Borrower(3, "Test Name", "Test Address", "Test Phone");
		assertEquals(3, borr.getCardNo());
	}
	
	@Test
	public void getNameTest() {
		Borrower borr = new Borrower(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Name", borr.getName());
	}
	
	@Test
	public void getAddressTest() {
		Borrower borr = new Borrower(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Address", borr.getAddress());
	}
	
	@Test
	public void getPhoneTest() {
		Borrower borr = new Borrower(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Phone", borr.getPhone());
	}
}
