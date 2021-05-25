/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.AirplaneType;

/**
 * @author Eric Colvin
 *
 */
public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {

	public AirplaneTypeDAO(Connection conn) {
		super(conn);
	}
	
	public void addAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("insert into airplane_type (max_capacity) values (?)", new Object[] {airplaneType.getMaxCapacity()});
	}
	
	public void updateAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("update airplane_type set max_capacity = ? where id = ?", new Object[] {airplaneType.getMaxCapacity(), airplaneType.getId()});
	}
	
	public void deleteAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("delete from airplane_type where id = ?", new Object[] {airplaneType.getId()});
	}
	
	public List<AirplaneType> getAirplaneTypes() throws ClassNotFoundException, SQLException {
		return read("select * from airplane_type", null);
	}
	
	public List<AirplaneType> getAirplaneType(int id) throws ClassNotFoundException, SQLException {
		return read("select * from airplane_type where id = ?", new Object[] {id});
	}
	
	@Override
	public List<AirplaneType> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<AirplaneType> airplaneTypes = new ArrayList<>();
		while(rs.next()) {
			AirplaneType airplaneType = new AirplaneType();
			airplaneType.setId(rs.getInt("id"));
			airplaneType.setMaxCapacity(rs.getInt("max_capacity"));
			airplaneTypes.add(airplaneType);
		}
		return airplaneTypes;
	}

}
