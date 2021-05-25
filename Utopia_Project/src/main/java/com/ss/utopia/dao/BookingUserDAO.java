/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingUser;

/**
 * @author Eric Colvin
 *
 */
public class BookingUserDAO extends BaseDAO<BookingUser>{

	public BookingUserDAO(Connection conn) {
		super(conn);
	}
	
	public void addBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
		save("insert into booking_user (booking_id, user_id) values (?, ?)", new Object[] {bu.getBooking().getId(), bu.getUser().getId()});
	}
	
	public void updateBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
		save("update booking_user set user_id = ? where booking_id = ?", new Object[] {bu.getUser().getId(), bu.getBooking().getId()});
	}
	
	public void deleteBookingUser(BookingUser bu) throws ClassNotFoundException, SQLException {
		save("delete from booking_user where booking_id = ?", new Object[] {bu.getBooking().getId()});
	}
	
	public List<BookingUser> getBookingUsers() throws ClassNotFoundException, SQLException {
		return read("select * from booking_user", null);
	}	
	
	public List<BookingUser> getBookingUsersByUID(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from booking_user where user_id = ?", new Object[] {id});
	}

	@Override
	public List<BookingUser> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingUser> bus = new ArrayList<>();
		while(rs.next()) {
			BookingUser bu = new BookingUser();
			bu.getBooking().setId(rs.getInt("booking_id"));
			bu.getUser().setId(rs.getInt("user_id"));
			bus.add(bu);
		}
		return bus;
	}

}
