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
public class LoanTest {
	@Test
	public void getBookIdTest() {
		Loan lo = new Loan(3, 4, 5, "Today", "Tomorrow");
		assertEquals(3, lo.getBookId());
	}
	
	@Test
	public void getBranchIdTest() {
		Loan lo = new Loan(3, 4, 5, "Today", "Tomorrow");
		assertEquals(4, lo.getBranchId());
	}
	
	@Test
	public void getCardNoTest() {
		Loan lo = new Loan(3, 4, 5, "Today", "Tomorrow");
		assertEquals(5, lo.getCardNo());
	}
	
	@Test
	public void getDateOutTest() {
		Loan lo = new Loan(3, 4, 5, "Today", "Tomorrow");
		assertEquals("Today", lo.getDateOut());
	}
	
	@Test
	public void getDueDateTest() {
		Loan lo = new Loan(3, 4, 5, "Today", "Tomorrow");
		assertEquals("Tomorrow", lo.getDueDate());
	}
}
