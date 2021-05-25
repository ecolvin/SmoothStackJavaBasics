/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class AirplaneType {

	private Integer id;
	private Integer maxCapacity;
	
	/**
	 * Default Constructor
	 */
	public AirplaneType() {
		id = -1;
		maxCapacity = -1;
	}

	/**
	 * @param id
	 * @param maxCapacity
	 */
	public AirplaneType(Integer id, Integer maxCapacity) {
		this.id = id;
		this.maxCapacity = maxCapacity;
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
	 * @return the maxCapacity
	 */
	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	
}
