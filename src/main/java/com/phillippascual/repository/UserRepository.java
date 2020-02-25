package com.phillippascual.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UserRepository {
	private static Logger log = Logger.getLogger(UserRepository.class);
	
	/**
	 * The insertNewUser() method takes in a String and a Connection.  It inserts the passed in username
	 * String into the public.username table and returns the number of rows in the table that were updated.
	 */
	public static int insertNewUser(String username, Connection conn) {

		String sqlStatement = "INSERT INTO public.usertable (username) VALUES (?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, username);
			final int rowsUpdated = stmt.executeUpdate();
			log.debug("User " + username + " created and added to database.");
			return rowsUpdated;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * The removeUser() method takes in a String and a Connection.  It removes the public.usertable record that
	 * matches the username that was passed in.
	 */
	public static int removeUser(String username, Connection conn) {
		String sqlStatement = "DELETE from public.usertable WHERE username = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, username);
			final int rowsUpdated = stmt.executeUpdate();
			log.debug("User " + username + " removed from database.");
			return rowsUpdated;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
