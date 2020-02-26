package com.phillippascual.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.phillippascual.repository.StockRepository;
import com.phillippascual.util.ConnectionUtil;

/**
 * UpdateStockServlet receives an empty GET request from the client.  It then instantiates a database connection and
 * invokes the StockRepository.updateStockPrices(), passing in the Connection it just instantiated.  It then returns
 * a message to the client stating that all portfolios have been updated.
 */
public class UpdateStocksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = ConnectionUtil.getConnection();
		StockRepository.updateStockPrices(conn);
		response.getWriter().append("Stock portfolios updated.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
