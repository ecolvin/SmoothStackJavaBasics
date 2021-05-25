/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class BookingUser {

	private Booking booking;
	private User user;
	/**
	 * Default Constructor
	 */
	public BookingUser() {
		booking = new Booking();
		user = new User();
	}
	/**
	 * @param booking
	 * @param user
	 */
	public BookingUser(Booking booking, User user) {
		this.booking = booking;
		this.user = user;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
