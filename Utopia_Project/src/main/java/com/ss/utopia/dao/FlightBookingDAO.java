/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.FlightBooking;

/**
 * @author Eric Colvin
 *
 */
public class FlightBookingDAO extends BaseDAO<FlightBooking> {

	public FlightBookingDAO(Connection conn) {
		super(conn);
	}
	
	public void addFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
		save("insert into flight_bookings (flight_id, booking_id) values (?, ?)", new Object[] {fb.getFlight().getId(), fb.getBooking().getId()});
	}
	
	/*public void updateFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
		save("update flight_bookings set  = ? where  = ?", new Object[] {});
	}*/
	
	public void deleteFlightBooking(FlightBooking fb) throws ClassNotFoundException, SQLException {
		save("delete from flight_bookings where flight_id = ? and booking_id = ?", new Object[] {fb.getFlight().getId(), fb.getBooking().getId()});
	}
	
	public List<FlightBooking> getFlightBookings() throws ClassNotFoundException, SQLException {
		return read("select * from flight_bookings", null);
	}
	
	public List<FlightBooking> getFlightBookingsByBID(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from flight_bookings where booking_id = ?", new Object[] {id});
	}
	
	@Override
	public List<FlightBooking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<FlightBooking> fbs = new ArrayList<>();
		while(rs.next()) {
			FlightBooking fb = new FlightBooking();
			fb.getFlight().setId(rs.getInt("flight_id"));
			fb.getBooking().setId(rs.getInt("booking_id"));
			fbs.add(fb);
		}
		return fbs;
	}

}
