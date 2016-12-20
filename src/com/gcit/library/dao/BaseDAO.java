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
	private Integer pageNo;
	private Integer pageSize = 10;
	
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
		
		if(this.pageNo != null && this.pageNo > 0) {
			Integer limit = (this.pageNo - 1) * this.pageSize;
			
			query += " LIMIT " + limit + " ," + this.pageSize;
		}
		
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
	
	public Integer getCount(String query, Object[] args) {
		PreparedStatement pstmt = null;
		try {
			pstmt = BaseDAO.connection.prepareStatement(query);
			
			if(args != null) {
				for (int i = 0; i < args.length; i++) {
					this.statement.setObject((i + 1), args[i]);
				}
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				return rs.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract <T>ArrayList<T> extractData(ResultSet resultSet, Boolean onlyBaseData);

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
