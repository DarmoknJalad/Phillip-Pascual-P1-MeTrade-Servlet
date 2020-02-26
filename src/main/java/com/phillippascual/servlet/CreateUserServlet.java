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
 * The CreateUserServlet receives a POST request from the client with the required parameters.  It retrieves the
 * parameter "username" from the client and passes that information to the UserRepository Class method insertNewUser().
 * It then writes a response acknowledging that the passed-in user has been added to the public.users database.
 */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		Connection conn = ConnectionUtil.getConnection();
		response.getWriter().println(UserRepository.insertNewUser(username, conn) + " user added.");
	}

}
