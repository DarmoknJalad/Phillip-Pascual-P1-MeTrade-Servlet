package com.phillippascual.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.phillippascual.repository.UserRepository;
import com.phillippascual.util.ConnectionUtil;

/**
 * The RemoveUserSerlvet receives a POST request from the client.  It retrieves the parameter "removeUser" from the
 * request and instantiates a database connection.  It then passes the "removeUser" parameter, as well as the database
 * Connection to the UserRepository.removeUser() method.
 */
public class RemoveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("removeUser");
		Connection conn = ConnectionUtil.getConnection();
		UserRepository.removeUser(username, conn);
		
	}

}
