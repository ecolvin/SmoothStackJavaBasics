/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Booking {

	private Integer id;
	private Integer isActive;
	private String confirmationCode;
	
	/**
	 * Default Constructor
	 */
	public Booking() {
		id = -1;
		isActive = -1;
		confirmationCode = "";
	}

	/**
	 * @param id
	 * @param isActive
	 * @param confirmationCode
	 */
	public Booking(Integer id, Integer isActive, String confirmationCode) {
		this.id = id;
		this.isActive = isActive;
		this.confirmationCode = confirmationCode;
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
	 * @return the isActive
	 */
	public Integer getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the confirmationCode
	 */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/**
	 * @param confirmationCode the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	
	
}
