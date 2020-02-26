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
 * The RemoveStockServlet takes in a POST request from the client with the appropriate parameters.  The doPost() method
 * then retrieves the parameters "username" and "ticker" and then instantiates a JDBC connection.  It then passes
 * these parameters and the Connection to the StockRepository.removeStock() method.
 */
public class RemoveStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String ticker = request.getParameter("ticker");
		
		Connection conn = ConnectionUtil.getConnection();
		
		StockRepository.removeStock(username, ticker, conn);
	}

}
