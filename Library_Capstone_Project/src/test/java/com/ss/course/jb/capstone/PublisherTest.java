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
public class PublisherTest {
	@Test
	public void getPublisherIdTest() {
		Publisher pub = new Publisher(3, "Test Name", "Test Address", "Test Phone");
		assertEquals(3, pub.getPublisherId());
	}
	
	@Test
	public void getPublisherNameTest() {
		Publisher pub = new Publisher(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Name", pub.getPublisherName());
	}
	
	@Test
	public void getPublisherAddressTest() {
		Publisher pub = new Publisher(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Address", pub.getPublisherAddress());
	}
	
	@Test
	public void getPublisherPhoneTest() {
		Publisher pub = new Publisher(3, "Test Name", "Test Address", "Test Phone");
		assertEquals("Test Phone", pub.getPublisherPhone());
	}
}
