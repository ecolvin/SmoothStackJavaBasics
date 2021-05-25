/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Booking;

/**
 * @author Eric Colvin
 *
 */
public class BookingDAO extends BaseDAO<Booking>{

	public BookingDAO(Connection conn) {
		super(conn);
	}

	public void addBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("insert into booking (is_active, confirmation_code) values (?, ?)", new Object[] {booking.getIsActive(), booking.getConfirmationCode()});
	}
	
	public void updateBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("update booking set is_active = ?, confirmation_code = ? where id = ?", new Object[] {booking.getIsActive(), booking.getConfirmationCode(), booking.getId()});
	}
	
	public void deleteBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("delete from booking where id = ?", new Object[] {booking.getId()});
	}
	
	public List<Booking> getBookings() throws ClassNotFoundException, SQLException {
		return read("select * from booking", null);
	}	
	
	public List<Booking> getBooking(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from booking where id = ?", new Object[] {id});
	}
	
	public List<Booking> getBooking(String code) throws ClassNotFoundException, SQLException {
		return read("select * from booking where confirmation_code = ?", new Object[] {code});
	}
	
	@Override
	public List<Booking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Booking> bookings = new ArrayList<>();
		while(rs.next()) {
			Booking booking = new Booking();
			booking.setId(rs.getInt("id"));
			booking.setIsActive(rs.getInt("is_active"));
			booking.setConfirmationCode(rs.getString("confirmation_code"));
			bookings.add(booking);
		}
		return bookings;
	}

}
