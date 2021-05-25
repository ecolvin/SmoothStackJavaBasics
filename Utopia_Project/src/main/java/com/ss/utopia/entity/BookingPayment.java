/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class BookingPayment {

	private Booking booking;
	private String stripeId;
	private Integer refunded;
	
	/**
	 * Default Constructor
	 */
	public BookingPayment() {
		booking = new Booking();
		stripeId = "Unknown";
		refunded = -1;
	}

	/**
	 * @param booking
	 * @param stripeId
	 * @param refunded
	 */
	public BookingPayment(Booking booking, String stripeId, Integer refunded) {
		this.booking = booking;
		this.stripeId = stripeId;
		this.refunded = refunded;
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
	 * @return the stripeId
	 */
	public String getStripeId() {
		return stripeId;
	}

	/**
	 * @param stripeId the stripeId to set
	 */
	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	/**
	 * @return the refunded
	 */
	public Integer getRefunded() {
		return refunded;
	}

	/**
	 * @param refunded the refunded to set
	 */
	public void setRefunded(Integer refunded) {
		this.refunded = refunded;
	}	
}
