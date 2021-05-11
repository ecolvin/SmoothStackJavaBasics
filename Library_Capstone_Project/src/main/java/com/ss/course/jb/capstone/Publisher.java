/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class Publisher {
	private int publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	
	public Publisher(int publisherId, String publisherName, String publisherAddress, String publisherPhone) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisherAddress = publisherAddress;
		this.publisherPhone = publisherPhone;
	}
	
	public int getPublisherId() {
		return publisherId;
	}
	
	public String getPublisherName() {
		return publisherName;
	}
	
	public String getPublisherAddress() {
		return publisherAddress;
	}
	
	public String getPublisherPhone() {
		return publisherPhone;
	}	
}
