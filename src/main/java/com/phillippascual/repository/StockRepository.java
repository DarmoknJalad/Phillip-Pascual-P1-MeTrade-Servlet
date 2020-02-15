package com.phillippascual.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class StockRepository {
	private static Logger log = Logger.getLogger(StockRepository.class);
	
	public static int addStock(String username, String ticker, double price, int qty, Connection conn) {
		String sqlStatement = "INSERT INTO public.stocks (ticker, userid, price, qty) "
				+ "VALUES (?, (SELECT userid FROM public.usertable WHERE username=?), "
				+ "(SELECT ?::double precision::numeric::money), ?)";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, ticker);
			stmt.setString(2, username);
			stmt.setDouble(3, price);
			stmt.setInt(4, qty);
			final int rowsUpdated = stmt.executeUpdate();
			log.debug("Stock " + ticker + " added to " + username + " portfolio.");
			return rowsUpdated;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int removeStock(String username, String ticker, Connection conn) {
		String sqlStatement = "DELETE FROM public.stocks WHERE userid=(SELECT userid FROM public.usertable WHERE "
				+ "username=?) AND ticker=?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, username);
			stmt.setString(2, ticker);
			final int rowsUpdated = stmt.executeUpdate();
			log.debug("Stock " + ticker + " belonging to " + username + " removed from portfolio.");
			return rowsUpdated;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
