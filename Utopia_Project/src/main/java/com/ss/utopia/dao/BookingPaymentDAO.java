/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.BookingPayment;

/**
 * @author Eric Colvin
 *
 */
public class BookingPaymentDAO extends BaseDAO<BookingPayment>{
	
	public BookingPaymentDAO(Connection conn) {
		super(conn);
	}

	public void addBookingPayment(BookingPayment bp) throws ClassNotFoundException, SQLException {
		save("insert into booking_payment (booking_id, stripe_id, refunded) values (?, ?, ?)", new Object[] {bp.getBooking().getId(), bp.getStripeId(), bp.getRefunded()});
	}
	
	public void updateBookingPayment(BookingPayment bp) throws ClassNotFoundException, SQLException {
		save("update booking_payment set stripe_id = ?, refunded = ? where booking_id = ?", new Object[] {bp.getStripeId(), bp.getRefunded(), bp.getBooking().getId()});
	}
	
	public void deleteBookingPayment(BookingPayment bp) throws ClassNotFoundException, SQLException {
		save("delete from booking_payment where booking_id = ?", new Object[] {bp.getBooking().getId()});
	}
	
	public List<BookingPayment> getBookingPayments() throws ClassNotFoundException, SQLException {
		return read("select * from booking_payment", null);
	}	
	
	@Override
	public List<BookingPayment> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingPayment> bps = new ArrayList<>();
		while(rs.next()) {
			BookingPayment bp = new BookingPayment();
			bp.getBooking().setId(rs.getInt("booking_id"));
			bp.setStripeId(rs.getString("stripe_id"));
			bp.setRefunded(rs.getInt("refunded"));
			bps.add(bp);
		}
		return bps;
	}
	
	
}
