package com.gcit.library.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	private static DatabaseManager instance;
	private Connection connection;

	private DatabaseManager() throws SQLException {
		connectToTheDatabse();
	}
	
	private void connectToTheDatabse() throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/library?useSSL=false",
				"root",
				"test"
		);
	}
	
	public static DatabaseManager getInstance() throws SQLException {
		if(DatabaseManager.instance == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not load driver");
				e.printStackTrace();
			}
			DatabaseManager.instance = new DatabaseManager();
		}
		
		return DatabaseManager.instance;
	}
	
	public void closeConnection() {
		try {
			this.getCurrentConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getCurrentConnection() {
		try {
			this.connectToTheDatabse();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.connection;
	}
}
