/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_publisher table
 * @author Eric Colvin
 *
 */
public class Publisher {
	private int publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	
	/**
	 * Simple Constructor
	 * @param publisherId - Publisher's Primary Key
	 * @param publisherName - Publisher's Name
	 * @param publisherAddress - Publisher's Location
	 * @param publisherPhone - Publisher's Contact Info
	 */
	public Publisher(int publisherId, String publisherName, String publisherAddress, String publisherPhone) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisherAddress = publisherAddress;
		this.publisherPhone = publisherPhone;
	}
	
	/**
	 * Simple getter for publisherId
	 * @return - publisherId
	 */
	public int getPublisherId() {
		return publisherId;
	}
	
	/**
	 * Simple getter for publisherName
	 * @return - publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	
	/**
	 * Simple getter for publisherAddress
	 * @return - publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}
	
	/**
	 * Simple getter for publisherPhone
	 * @return - publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}	
}
