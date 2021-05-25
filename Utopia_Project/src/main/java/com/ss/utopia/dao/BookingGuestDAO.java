/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingGuest;

/**
 * @author Eric Colvin
 *
 */
public class BookingGuestDAO extends BaseDAO<BookingGuest>{

	public BookingGuestDAO(Connection conn) {
		super(conn);
	}

	public void addBookingGuest(BookingGuest bg) throws ClassNotFoundException, SQLException {
		save("insert into booking_guest (booking_id, contact_email, contact_phone) values (?, ?, ?)", new Object[] {bg.getBooking().getId(), bg.getEmail(), bg.getPhone()});
	}
	
	public void updateBookingGuest(BookingGuest bg) throws ClassNotFoundException, SQLException {
		save("update booking_guest set contact_email = ?, contact_phone = ? where booking_id = ?", new Object[] {bg.getEmail(), bg.getPhone(), bg.getBooking().getId()});
	}
	
	public void deleteBookingGuest(BookingGuest bg) throws ClassNotFoundException, SQLException {
		save("delete from booking_guest where booking_id = ?", new Object[] {bg.getBooking().getId()});
	}
	
	public List<BookingGuest> getBookingGuests() throws ClassNotFoundException, SQLException {
		return read("select * from booking_guest", null);
	}
	
	@Override
	public List<BookingGuest> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingGuest> bgs = new ArrayList<>();
		while(rs.next()) {
			BookingGuest bg = new BookingGuest();
			bg.getBooking().setId(rs.getInt("booking_id"));
			bg.setEmail(rs.getString("contact_email"));
			bg.setPhone(rs.getString("contact_phone"));
			bgs.add(bg);
		}
		return bgs;
	}

}
