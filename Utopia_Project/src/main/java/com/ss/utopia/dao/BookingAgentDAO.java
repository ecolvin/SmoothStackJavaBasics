/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingAgent;

/**
 * @author Eric Colvin
 *
 */
public class BookingAgentDAO extends BaseDAO<BookingAgent>{

	public BookingAgentDAO(Connection conn) {
		super(conn);
	}

	public void addBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
		save("insert into booking_agent (booking_id, agent_id) values (?, ?)", new Object[] {ba.getBooking().getId(), ba.getAgent().getId()});
	}
	
	public void updateBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
		save("update booking_agent set agent_id = ? where booking_id = ?", new Object[] {ba.getAgent().getId(), ba.getBooking().getId()});
	}
	
	public void deleteBookingAgent(BookingAgent ba) throws ClassNotFoundException, SQLException {
		save("delete from booking_agent where booking_id = ?", new Object[] {ba.getBooking().getId()});
	}
	
	public List<BookingAgent> getBookingAgents() throws ClassNotFoundException, SQLException {
		return read("select * from booking_agent", null);
	}
	
	@Override
	public List<BookingAgent> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingAgent> bas = new ArrayList<>();
		while(rs.next()) {
			BookingAgent ba = new BookingAgent();
			ba.getBooking().setId(rs.getInt("booking_id"));
			ba.getAgent().setId(rs.getInt("agent_id"));
			bas.add(ba);
		}
		return bas;
	}

}
