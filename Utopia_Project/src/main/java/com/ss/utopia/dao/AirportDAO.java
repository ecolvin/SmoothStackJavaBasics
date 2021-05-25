/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Airport;

/**
 * @author Eric Colvin
 *
 */
public class AirportDAO extends BaseDAO<Airport> {

	public AirportDAO(Connection conn) {
		super(conn);
	}
	
	public void addAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("insert into airport (iata_id, city) values (?, ?)", new Object[] {airport.getAirportCode(), airport.getCityName()});
	}
	
	public void updateAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("update airport set city = ? where iata_id = ?", new Object[] {airport.getCityName(), airport.getAirportCode()});
	}
	
	public void deleteAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("delete from airport where iata_id = ?", new Object[] {airport.getAirportCode()});
	}
	
	public List<Airport> getAirports() throws ClassNotFoundException, SQLException {
		return read("select * from airport", null);
	}
	
	public List<Airport> getAirport(String airportCode) throws ClassNotFoundException, SQLException {
		return read("select * from airport where iata_id = ?", new Object[] {airportCode});
	}
	
	@Override
	public List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airport> airports = new ArrayList<>();
		while(rs.next()) {
			Airport airport = new Airport();
			airport.setAirportCode(rs.getString("iata_id"));
			airport.setCityName(rs.getString("city"));
			airports.add(airport);
		}
		return airports;
	}	
}
