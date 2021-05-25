/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.UserRole;

/**
 * @author Eric Colvin
 *
 */
public class UserRoleDAO extends BaseDAO<UserRole> {

	public UserRoleDAO(Connection conn) {
		super(conn);
	}

	public void addUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("insert into user_role (name) values (?)", new Object[] {userRole.getName()});
	}
	
	public void updateUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("update user_role set name = ? where id = ?", new Object[] {userRole.getName(), userRole.getId()});
	}
	
	public void deleteUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("delete from user_role where id = ?", new Object[] {userRole.getId()});
	}
	
	public List<UserRole> getUserRoles() throws ClassNotFoundException, SQLException {
		return read("select * from user_role", null);
	}	
	
	public List<UserRole> getUserRole(int id) throws ClassNotFoundException, SQLException {
		return read("select * from user_role where id = ?", new Object[]{id});
	}
	
	
	@Override
	public List<UserRole> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<UserRole> userRoles = new ArrayList<>();
		while(rs.next()) {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt("id"));
			userRole.setName(rs.getString("name"));
			userRoles.add(userRole);
		}
		return userRoles;
	}
}
