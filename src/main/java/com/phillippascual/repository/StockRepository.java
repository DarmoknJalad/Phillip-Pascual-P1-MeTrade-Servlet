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
