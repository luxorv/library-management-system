package com.gcit.library.service;

import java.sql.SQLException;

import com.gcit.library.dao.BaseDAO;
import com.gcit.library.model.DatabaseManager;

public abstract class BaseService {
	public static void startConnection() throws SQLException {
		BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
	}
	
	public static void startTransaction() throws SQLException {
		startConnection();
		DatabaseManager.getInstance().getCurrentConnection().setAutoCommit(false);
	}
	
	public static void closeConnection() throws SQLException {
		DatabaseManager.getInstance().getCurrentConnection().close();
	}
	
	public static void commitChanges() throws SQLException {
		DatabaseManager.getInstance().getCurrentConnection().commit();
	}
}
