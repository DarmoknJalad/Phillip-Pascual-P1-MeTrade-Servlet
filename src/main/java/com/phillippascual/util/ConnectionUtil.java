package com.phillippascual.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection () {
		Connection conn = null;
		log.debug("Attempting connection to DB.");
		try {
			Class.forName("org.postgresql.Driver");
			Properties props = new Properties();
			props.load(ConnectionUtil.class.getClassLoader().getResourceAsStream("app.properties"));
			conn = DriverManager.getConnection(props.getProperty("url"),
											   props.getProperty("user"),
											   props.getProperty("password")
											   );
			if (conn.isValid(5)) {
				log.debug("Connection successful.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			log.debug("Connection to DB failed.");
			e.printStackTrace();
		}
		return conn;
		
	}
}
