/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * A simple object to hold the data for an entry in the tbl_borrower table
 * @author Eric Colvin
 *
 */
public class Borrower {
	private int cardNo;
	private String name;
	private String address;
	private String phone;
	
	/**
	 * Simple Constructor
	 * @param cardNo - Borrower's Primary Key
	 * @param name - Borrower's Name
	 * @param address - Borrower's Location
	 * @param phone - Borrower's Contact Info
	 */
	public Borrower(int cardNo, String name, String address, String phone) {
		this.cardNo = cardNo;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	/**
	 * Simple getter for cardNo
	 * @return - cardNo
	 */
	public int getCardNo() {
		return cardNo;
	}
	
	/**
	 * Simple getter for getName
	 * @return - getName
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Simple getter for address
	 * @return - address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Simple getter for phone
	 * @return - phone
	 */
	public String getPhone() {
		return phone;
	}
}
