/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Eric Colvin
 *
 */
public class Flight {

	private Integer id;
	private Route route;
	private Airplane airplane;
	private String departure;
	private Integer reservedSeats;
	private float price;
	
	/**
	 * Default Constructor
	 */
	public Flight() {
		id = -1;
		route = new Route();
		airplane = new Airplane();
		departure = "Unknown";
		reservedSeats = -1;
		price = -1;
	}

	/**
	 * @param id
	 * @param route
	 * @param airplane
	 * @param departure
	 * @param reservedSeats
	 * @param price
	 */
	public Flight(Integer id, Route route, Airplane airplane, String departure, Integer reservedSeats, float price) {
		this.id = id;
		this.route = route;
		this.airplane = airplane;
		this.departure = departure;
		this.reservedSeats = reservedSeats;
		this.price = price;
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
	 * @return the route
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * @return the airplane
	 */
	public Airplane getAirplane() {
		return airplane;
	}

	/**
	 * @param airplane the airplane to set
	 */
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return departure;
	}

	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		this.departure = departure;
	}

	/**
	 * @return the reservedSeats
	 */
	public Integer getReservedSeats() {
		return reservedSeats;
	}

	/**
	 * @param reservedSeats the reservedSeats to set
	 */
	public void setReservedSeats(Integer reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	
}
