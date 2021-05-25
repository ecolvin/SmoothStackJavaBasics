/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Route {

	private Integer id;
	private Airport originAirport;
	private Airport destAirport;
	
	/**
	 * Default Constructor
	 */
	public Route() {
		id = -1;
		originAirport = new Airport();
		destAirport = new Airport();		
	}

	/**
	 * @param id
	 * @param originAirport
	 * @param destAirport
	 */
	public Route(Integer id, Airport originAirport, Airport destAirport) {
		this.id = id;
		this.originAirport = originAirport;
		this.destAirport = destAirport;
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
	 * @return the originAirport
	 */
	public Airport getOriginAirport() {
		return originAirport;
	}

	/**
	 * @param originAirport the originAirport to set
	 */
	public void setOriginAirport(Airport originAirport) {
		this.originAirport = originAirport;
	}

	/**
	 * @return the destAirport
	 */
	public Airport getDestAirport() {
		return destAirport;
	}

	/**
	 * @param destAirport the destAirport to set
	 */
	public void setDestAirport(Airport destAirport) {
		this.destAirport = destAirport;
	}
	
	

}
