/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Airport {	
	
	private String airportCode;
	private String cityName;

	/**
	 * Default Constructor
	 */
	public Airport() {
		airportCode = "N/A";
		cityName = "Unknown";
	}

	/**
	 * @param airportCode
	 * @param cityName
	 */
	public Airport(String airportCode, String cityName) {
		this.airportCode = airportCode;
		this.cityName = cityName;
	}

	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
