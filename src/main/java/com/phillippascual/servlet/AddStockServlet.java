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
 * Servlet implementation class AddStockServlet.  The servlet receives a POST request.  It retrieves the parameters
 * "username," ticker," "price," and "qty" from the request, and then instantiates a JDBC connection.  It then calls
 * the StockRepository Class method addStock().
 */
public class AddStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String ticker = request.getParameter("ticker");
		double price = Double.parseDouble(request.getParameter("price"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		
		Connection conn = ConnectionUtil.getConnection();
		StockRepository.addStock(username, ticker, price, qty, conn);
	}

}
