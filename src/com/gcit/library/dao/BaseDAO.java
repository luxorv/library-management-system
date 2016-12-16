package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class BaseDAO {

	public static Connection connection = null;
	private PreparedStatement statement;
	private ResultSet resultSet;
	
	public static void setConnection(Connection conn) {
		BaseDAO.connection = conn;
	}
	
	public Integer alter(String query, Object[] args) {
		this.statement = null;
		
		try {
			this.statement = BaseDAO.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if(args != null) {
				for (int i = 0; i < args.length; i++) {
					this.statement.setObject((i + 1), args[i]);
				}
				this.statement.executeUpdate();
				
				this.resultSet = this.statement.getGeneratedKeys();
				
				BaseDAO.connection.commit();
				
				if(this.resultSet.next()) {
					return this.resultSet.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T>ArrayList<T> getAll(String query, Boolean onlyBaseData, Object args[]) {
		this.statement = null;
		
		try {
			this.statement = BaseDAO.connection.prepareStatement(query);
			
			if(args != null) {
				for (int i = 0; i < args.length; i++) {
					this.statement.setObject((i + 1), args[i]);
				}
			}
			
			this.resultSet = this.statement.executeQuery();
			
			return extractData(this.resultSet, onlyBaseData); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public abstract <T>ArrayList<T> extractData(ResultSet resultSet, Boolean onlyBaseData);
	
}
