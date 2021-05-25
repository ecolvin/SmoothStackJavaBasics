/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class BookingGuest {

	private Booking booking;
	private String email;
	private String phone;
	
	/**
	 * Default Constructor
	 */
	public BookingGuest() {
		booking = new Booking();
		email = "Unknown";
		phone = "Unknown";
	}

	/**
	 * @param booking
	 * @param email
	 * @param phone
	 */
	public BookingGuest(Booking booking, String email, String phone) {
		this.booking = booking;
		this.email = email;
		this.phone = phone;
	}

	/**
	 * @return the booking
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * @param booking the booking to set
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
