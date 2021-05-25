/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Passenger {

	private Integer id;
	private Booking booking;
	private String firstName;
	private String lastName;
	private String birthday;
	private String gender;
	private String address;
	
	/**
	 * Default Constructor
	 */
	public Passenger() {
		id = -1;
		booking = new Booking();
		firstName = "Unknown";
		lastName = "Unknown";
		birthday = "0001-01-01";
		gender = "Unknown";
		address = "Unknown";
	}

	/**
	 * @param id
	 * @param booking
	 * @param firstName
	 * @param lastName
	 * @param birthday
	 * @param gender
	 * @param address
	 */
	public Passenger(Integer id, Booking booking, String firstName, String lastName, String birthday, String gender,
			String address) {
		this.id = id;
		this.booking = booking;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
		this.address = address;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
