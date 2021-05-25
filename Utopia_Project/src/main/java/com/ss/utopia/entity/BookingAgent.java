/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class BookingAgent {

	private Booking booking;
	private User agent;
	
	/**
	 * Default Constructor
	 */
	public BookingAgent() {
		booking = new Booking();
		agent = new User();
	}

	/**
	 * @param booking
	 * @param agent
	 */
	public BookingAgent(Booking booking, User agent) {
		this.booking = booking;
		this.agent = agent;
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
	 * @return the agent
	 */
	public User getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(User agent) {
		this.agent = agent;
	}
	
	
}
