/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class FlightBooking {

	private Flight flight;
	private Booking booking;
	
	/**
	 * Default Constructor
	 */
	public FlightBooking() {
		flight = new Flight();
		booking = new Booking();
	}

	/**
	 * @param flight
	 * @param booking
	 */
	public FlightBooking(Flight flight, Booking booking) {
		this.flight = flight;
		this.booking = booking;
	}

	/**
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * @param flight the flight to set
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
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
}
