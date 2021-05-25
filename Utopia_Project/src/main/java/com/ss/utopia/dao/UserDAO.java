/**
 * 
 */
package com.ss.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.User;

/**
 * @author Eric Colvin
 *
 */
public class UserDAO extends BaseDAO<User>{

	public UserDAO(Connection conn) {
		super(conn);
	}

	public void addUser(User user) throws ClassNotFoundException, SQLException {
		save("insert into user (role_id, given_name, family_name, username, email, password, phone) values (?, ?, ?, ?, ?, ?, ?)", new Object[] {user.getRole().getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone()});
	}
	
	public void updateUser(User user) throws ClassNotFoundException, SQLException {
		save("update user set role_id = ?, given_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? where id = ?", new Object[] {user.getRole().getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone(), user.getId()});
	}
	
	public void deleteUser(User user) throws ClassNotFoundException, SQLException {
		save("delete from user where id = ?", new Object[] {user.getId()});
	}
	
	public List<User> getUsers() throws ClassNotFoundException, SQLException {
		return read("select * from user", null);
	}
	
	public List<User> getUser(int id) throws ClassNotFoundException, SQLException {
		return read("select * from user where id = ?", new Object[] {id});
	}
	
	@Override
	public List<User> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<User> users = new ArrayList<>();
		while(rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.getRole().setId(rs.getInt("role_id"));
			user.setFirstName(rs.getString("given_name"));
			user.setLastName(rs.getString("family_name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setPhone(rs.getString("phone"));
			users.add(user);
		}
		return users;
	}

}
