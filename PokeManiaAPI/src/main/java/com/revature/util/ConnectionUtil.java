package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	//Make this work for servlets
	static {
		
		try {
			
			Class.forName("oracle.jdbc.OracleDriver");
			
		} catch (ClassNotFoundException e) {
			
			System.err.println(e.getMessage());
			
		}
		
	}
	
	public static Connection getConnection() throws SQLException {
		
		String		url			= System.getenv("POKEMANIA_DB_URL"),
					username	= System.getenv("POKEMANIA_DB_USERNAME"),
					password	= System.getenv("POKEMANIA_DB_PASSWD");
		
		return DriverManager.getConnection(url, username, password);
		
	}

}
