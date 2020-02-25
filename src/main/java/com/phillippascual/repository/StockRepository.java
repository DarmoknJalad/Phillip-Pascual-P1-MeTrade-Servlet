package com.phillippascual.repository;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * The StockRepository Class contains all DDL and DML operations for the database used for this project.
 */
public class StockRepository {
	private static Logger log = Logger.getLogger(StockRepository.class);
	
	/**
	 * The addStock() method takes in 5 parameters: String username, String ticker, double price, int qty,
	 * and Connection conn.  The method creates a Prepared SQL Statment that takes in the username, ticker, price,
	 * and qty, and inserts a stock with the passed-in information into the public.stocks table.  This method first
	 * requires that a user be added to the public.users table in order to satisfy one value in the SQL command.
	 */
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
	
	/**
	 * The removeStock() method takes in 3 parameters: String username, String ticker, and Connection conn.
	 * It generates a SQL statement that deletes 'ticker' from public.stocks for the username passed into the method.
	 * It returns an in of the number of records updated, which should be 1 since there should be no duplicate records
	 * in the public.stocks table.
	 */
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
	
	/**
	 * The updateStockPrices() method takes in a Connection parameter.  It first retrieves a ResultSet of 
	 * stock tickers from the public.stocks table.  It then iterates through the ResultSet and passes each ticker
	 * into Mule to return a double of the stock price for each stock ticker.  It then updates the records with each
	 * stock ticker in the public.stocks table.  The method then returns the total number of records updated.
	 */
	public static int updateStockPrices(Connection conn) {
		String sqlStatement = "SELECT ticker FROM public.stocks";
		String sqlStatement2 = "UPDATE public.stocks SET price = ?::double precision::numeric::money"
				+ " WHERE ticker = ?";
		int updated = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ticker = rs.getString(1);
				URL url = new URL("http://localhost:8083/stockprice?tickerSymbol=" + ticker);
				HttpURLConnection soapUrl = (HttpURLConnection) url.openConnection();
				InputStream input = soapUrl.getInputStream();
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(input);
				double price = scan.nextDouble();
				PreparedStatement stmt2 = conn.prepareStatement(sqlStatement2);
				stmt2.setDouble(1, price);
				stmt2.setString(2,  ticker);
				updated += stmt2.executeUpdate();
			}
			return updated;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
}
