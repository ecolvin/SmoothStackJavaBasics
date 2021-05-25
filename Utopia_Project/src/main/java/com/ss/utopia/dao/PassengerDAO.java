/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Passenger;

/**
 * @author Eric Colvin
 *
 */
public class PassengerDAO extends BaseDAO<Passenger>{
	
	public PassengerDAO(Connection conn) {
		super(conn);
	}

	public void addPassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("insert into passenger (booking_id, given_name, family_name, dob, gender, address) values (?, ?, ?, ?, ?, ?)", new Object[] {passenger.getBooking().getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getBirthday(), passenger.getGender(), passenger.getAddress()});
	}
	
	public void updatePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("update passenger set booking_id = ?, given_name = ?, family_name = ?, dob = ?, gender = ?, address = ? where id = ?", new Object[] {passenger.getBooking().getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getBirthday(), passenger.getGender(), passenger.getAddress(), passenger.getId()});
	}
	
	public void deletePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("delete from passenger where id = ?", new Object[] {passenger.getId()});
	}
	
	public List<Passenger> getPassengers() throws ClassNotFoundException, SQLException {
		return read("select * from passenger", null);
	}
	
	@Override
	public List<Passenger> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Passenger> passengers = new ArrayList<>();
		while(rs.next()) {
			Passenger passenger = new Passenger();
			passenger.setId(rs.getInt("id"));
			passenger.getBooking().setId(rs.getInt("booking_id"));
			passenger.setFirstName(rs.getString("given_name"));
			passenger.setLastName(rs.getString("family_name"));
			passenger.setBirthday(rs.getDate("dob").toString());
			passenger.setGender(rs.getString("gender"));
			passenger.setAddress(rs.getString("address"));
			passengers.add(passenger);
		}
		return passengers;
	}

}
