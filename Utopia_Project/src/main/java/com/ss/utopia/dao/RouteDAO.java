/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.Route;

/**
 * @author Eric Colvin
 *
 */
public class RouteDAO extends BaseDAO<Route> {

	public RouteDAO(Connection conn) {
		super(conn);
	}
	
	public void addRoute(Route route) throws ClassNotFoundException, SQLException {
		save("insert into route (origin_id, destination_id) values (?, ?)", new Object[] {route.getOriginAirport().getAirportCode(), route.getDestAirport().getAirportCode()});
	}
	
	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("update route set origin_id = ?, destination_id = ? where id = ?", new Object[] {route.getOriginAirport().getAirportCode(), route.getDestAirport().getAirportCode(), route.getId()});
	}
	
	public void deleteRoute(Route route) throws ClassNotFoundException, SQLException {
		save("delete from route where id = ?", new Object[] {route.getId()});
	}
	
	public List<Route> getRoutes() throws ClassNotFoundException, SQLException {
		return read("select * from route", null);
	}	
	
	public List<Route> getRoute(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from route where id = ?", new Object[] {id});
	}	
	
	@Override
	public List<Route> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Route> routes = new ArrayList<>();
		while(rs.next()) {
			Route route = new Route();
			route.setId(rs.getInt("id"));
			route.getOriginAirport().setAirportCode(rs.getString("origin_id"));
			route.getDestAirport().setAirportCode(rs.getString("destination_id"));
			routes.add(route);
		}
		return routes;
	}
}
