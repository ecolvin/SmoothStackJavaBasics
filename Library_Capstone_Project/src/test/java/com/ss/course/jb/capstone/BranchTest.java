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
public class BranchTest {
	@Test
	public void getBranchIdTest() {
		Branch br = new Branch(3, "Test Name", "Test Address");
		assertEquals(3, br.getBranchId());
	}
	
	@Test
	public void getBranchNameTest() {
		Branch br = new Branch(3, "Test Name", "Test Address");
		assertEquals("Test Name", br.getBranchName());
	}
	
	@Test
	public void getBranchAddressTest() {
		Branch br = new Branch(3, "Test Name", "Test Address");
		assertEquals("Test Address", br.getBranchAddress());
	}
}
