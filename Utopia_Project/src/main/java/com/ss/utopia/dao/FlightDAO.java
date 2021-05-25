package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Flight;
/**
 * @author Eric Colvin
 *
 */
public class FlightDAO extends BaseDAO<Flight> {
	
	public FlightDAO(Connection conn) {
		super(conn);
	}
	
	public void addFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("insert into flight (id, route_id, airplane_id, departure_time, reserved_seats, seat_price) values (?, ?, ?, ?, ?, ?)", new Object[] {flight.getId(), flight.getRoute().getId(), flight.getAirplane().getId(), flight.getDeparture(), flight.getReservedSeats(), flight.getPrice()});
	}
	
	public void updateFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("update flight set route_id = ?, airplane_id = ?, departure_time = ?, reserved_seats = ?, seat_price = ? where id = ?", new Object[] {flight.getRoute().getId(), flight.getAirplane().getId(), flight.getDeparture(), flight.getReservedSeats(), flight.getPrice(), flight.getId()});
	}
	
	public void deleteFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("delete from flight where id = ?", new Object[] {flight.getId()});
	}
	
	public List<Flight> getFlights() throws ClassNotFoundException, SQLException {
		return read("select * from flight", null);
	}	
	
	public List<Flight> getFlight(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from flight where id = ?", new Object[] {id});
	}
	
	@Override
	public List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Flight> flights = new ArrayList<>();
		while(rs.next()) {
			Flight flight = new Flight();
			flight.setId(rs.getInt("id"));
			flight.getRoute().setId(rs.getInt("route_id"));
			flight.getAirplane().setId(rs.getInt("airplane_id"));
			flight.setDeparture(rs.getTimestamp("departure_time").toString());
			flight.setReservedSeats(rs.getInt("reserved_seats"));
			flight.setPrice(rs.getFloat("seat_price"));
			flights.add(flight);
		}
		return flights;
	}
}
