/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Airplane {

	private Integer id;
	private AirplaneType type;
	
	/**
	 * Default Constructor
	 */
	public Airplane() {
		id = -1;
		type = new AirplaneType();
	}

	/**
	 * @param id
	 * @param type
	 */
	public Airplane(Integer id, AirplaneType type) {
		this.id = id;
		this.type = type;
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
	 * @return the type
	 */
	public AirplaneType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AirplaneType type) {
		this.type = type;
	}
	
	
}
