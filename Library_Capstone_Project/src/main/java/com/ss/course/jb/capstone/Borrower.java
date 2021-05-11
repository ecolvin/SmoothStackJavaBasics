/**
 * 
 */
package com.ss.course.jb.capstone;

/**
 * @author Eric Colvin
 *
 */
public class Borrower {
	private int cardNo;
	private String name;
	private String address;
	private String phone;
	
	public Borrower(int cardNo, String name, String address, String phone) {
		this.cardNo = cardNo;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public int getCardNo() {
		return cardNo;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhone() {
		return phone;
	}
}
