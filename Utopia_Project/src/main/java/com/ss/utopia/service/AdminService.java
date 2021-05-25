/**
 * 
 */
package com.ss.utopia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.dao.AirplaneDAO;
import com.ss.utopia.dao.AirplaneTypeDAO;
import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.BookingDAO;
import com.ss.utopia.dao.BookingUserDAO;
import com.ss.utopia.dao.FlightBookingDAO;
import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.dao.RouteDAO;
import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.dao.UserRoleDAO;
import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.Route;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

/**
 * @author Eric Colvin
 *
 */
public class AdminService {

	ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Flight> getFlights() {
		Connection conn = null;
		List<Flight> flights = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			RouteDAO rdao = new RouteDAO(conn);
			AirplaneDAO planedao = new AirplaneDAO(conn);
			AirplaneTypeDAO typedao = new AirplaneTypeDAO(conn);
			AirportDAO portdao = new AirportDAO(conn);			
			flights = fdao.getFlights();
			for(Flight f: flights) { 
				f.setRoute(rdao.getRoute(f.getRoute().getId()).get(0));
				f.getRoute().setOriginAirport(portdao.getAirport(f.getRoute().getOriginAirport().getAirportCode()).get(0));
				f.getRoute().setDestAirport(portdao.getAirport(f.getRoute().getDestAirport().getAirportCode()).get(0));
				f.setAirplane(planedao.getAirplane(f.getAirplane().getId()).get(0));
				f.getAirplane().setType(typedao.getAirplaneType(f.getAirplane().getType().getId()).get(0));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return flights;
	}
	
	public List<Airport> getAirports() {
		Connection conn = null;
		List<Airport> airports = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			airports = adao.getAirports();
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return airports;
	}
	
	public List<Route> getRoutes() {
		Connection conn = null;
		List<Route> routes = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			RouteDAO rdao = new RouteDAO(conn);
			routes = rdao.getRoutes();
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return routes;
	}
	
	public List<User> getUsers() {
		Connection conn = null;
		List<User> users = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			UserDAO udao = new UserDAO(conn);
			UserRoleDAO urdao = new UserRoleDAO(conn);
			users = udao.getUsers();
			for(User u : users) {
				u.setRole(urdao.getUserRole(u.getRole().getId()).get(0));
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return users;
	}
	
	public List<Airplane> getAirplanes() {
		Connection conn = null;
		List<Airplane> airplanes = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			AirplaneDAO adao = new AirplaneDAO(conn);
			AirplaneTypeDAO atdao = new AirplaneTypeDAO(conn);
			airplanes = adao.getAirplanes();
			for(Airplane a : airplanes) {
				a.setType(atdao.getAirplaneType(a.getType().getId()).get(0));
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return airplanes;
	}
	
	public List<UserRole> getUserRoles() {
		Connection conn = null;
		List<UserRole> userRoles = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			UserRoleDAO urdao = new UserRoleDAO(conn);
			userRoles = urdao.getUserRoles();
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return userRoles;
	}
	
	public List<Flight> getFlight(Integer id) {
		Connection conn = null;
		List<Flight> flights = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			flights = fdao.getFlight(id);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return flights;
	}
	
	
	
	public void addAirport(Airport a) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			adao.addAirport(a);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void addRoute(Route r) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			RouteDAO rdao = new RouteDAO(conn);
			rdao.addRoute(r);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void addFlight(Flight f) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.addFlight(f);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void addUser(User u) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			UserDAO udao = new UserDAO(conn);
			udao.addUser(u);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public int updateFlight(Flight f) {
		Connection conn = null;
		int exit_status = 0;
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			AirportDAO adao = new AirportDAO(conn);
			if(adao.getAirport(f.getRoute().getOriginAirport().getAirportCode()).size() == 0) {
				exit_status -= 1;
			}
			if(adao.getAirport(f.getRoute().getDestAirport().getAirportCode()).size() == 0) {
				exit_status -= 2;
			}
			if(exit_status == 0) {
				fdao.updateFlight(f);
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return exit_status;
	}
	
	public int updateAirport(Airport a) {
		Connection conn = null;
		int exit_status = 0;
		try {
			conn = connUtil.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			adao.updateAirport(a);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return exit_status;
	}
	
	public int updateRoute(Route r) {
		Connection conn = null;
		int exit_status = 0;
		try {
			conn = connUtil.getConnection();
			RouteDAO rdao = new RouteDAO(conn);
			AirportDAO adao = new AirportDAO(conn);
			if(adao.getAirport(r.getOriginAirport().getAirportCode()).size() == 0) {
				exit_status -= 1;
			}
			if(adao.getAirport(r.getDestAirport().getAirportCode()).size() == 0) {
				exit_status -= 2;
			}
			if(exit_status == 0) {
				rdao.updateRoute(r);
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return exit_status;
	}
	
	public int updateUser(User u) {
		Connection conn = null;
		int exit_status = 0;
		try {
			conn = connUtil.getConnection();
			UserDAO udao = new UserDAO(conn);
			UserRoleDAO urdao = new UserRoleDAO(conn);
			if(urdao.getUserRole(u.getRole().getId()).size() == 0) {
				exit_status -= 1;
			}
			if(exit_status == 0) {
				udao.updateUser(u);
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return exit_status;
	}
	
	public User validateTraveler(int id) {
		Connection conn = null;
		User user = null;
		try {
			conn = connUtil.getConnection();
			UserDAO udao = new UserDAO(conn);
			List<User> users = udao.getUser(id);
			if(users.size() != 0) {
				user = users.get(0);
				UserRoleDAO urdao = new UserRoleDAO(conn);
				user.setRole(urdao.getUserRole(user.getRole().getId()).get(0));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return user;
	}
	
	public void addBooking(Flight f, User u) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookingUserDAO budao = new BookingUserDAO(conn);
			BookingDAO bdao = new BookingDAO(conn);
			FlightBookingDAO fbdao = new FlightBookingDAO(conn);
			Booking b = new Booking();
			b.setIsActive(1);
			b.setConfirmationCode(f.getRoute().getOriginAirport().getAirportCode() + f.getRoute().getDestAirport().getAirportCode() + u.getUsername());
			bdao.addBooking(b);
			b = bdao.getBooking(b.getConfirmationCode()).get(0);
			
			BookingUser bu = new BookingUser(b, u);
			FlightBooking fb = new FlightBooking(f, b);
						
			budao.addBookingUser(bu);			
			fbdao.addFlightBooking(fb);
			f.setReservedSeats(f.getReservedSeats() + 1);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}		
	}
	
	public List<FlightBooking> getFlightBookings(User u) {
		Connection conn = null;
		List<FlightBooking> fbs = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			FlightBookingDAO fbdao = new FlightBookingDAO(conn);
			BookingDAO bdao = new BookingDAO(conn);
			BookingUserDAO budao = new BookingUserDAO(conn);
			FlightDAO fdao = new FlightDAO(conn);
			RouteDAO rdao = new RouteDAO(conn);
			List<BookingUser> bus = budao.getBookingUsersByUID(u.getId());
			for(BookingUser bu : bus) {
				List<Booking> booking = bdao.getBooking(bu.getBooking().getId());
				if(booking.size() != 0) {
					List<FlightBooking> bookedFlights = fbdao.getFlightBookingsByBID(booking.get(0).getId());
					for(FlightBooking fb : bookedFlights) {
						fb.setBooking(booking.get(0));
						fb.setFlight(fdao.getFlight(fb.getFlight().getId()).get(0));
						fb.getFlight().setRoute(rdao.getRoute(fb.getFlight().getRoute().getId()).get(0));
						fbs.add(fb);
					}
				} 				
			}			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		return fbs;
	}
	
	public void deleteBooking(Booking b) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookingDAO bdao = new BookingDAO(conn);
			bdao.deleteBooking(b);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void deleteAirport(Airport a) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AirportDAO adao = new AirportDAO(conn);
			adao.deleteAirport(a);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void deleteRoute(Route r) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			RouteDAO rdao = new RouteDAO(conn);
			rdao.deleteRoute(r);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void deleteFlight(Flight f) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.deleteFlight(f);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	public void deleteUser(User u) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			UserDAO udao = new UserDAO(conn);
			udao.deleteUser(u);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
}
