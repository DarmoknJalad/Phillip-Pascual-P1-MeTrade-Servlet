package com.phillippascual.main;

import java.sql.Connection;

import com.phillippascual.util.ConnectionUtil;

public class MeTrade {
	public static Connection conn;
	
	public static void main(String[] args) {
		conn = ConnectionUtil.getConnection();

	}

}
