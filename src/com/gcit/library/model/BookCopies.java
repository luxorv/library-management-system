package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCopies implements Savable {

	private int bookId;
	private int branchId;
	private int noOfCopies;
	private boolean found;
	
	public BookCopies(int bookId, int branchId, int noOfCopies) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
		this.found = false;
	}
	
	public static BookCopies get(int bookId, int branchId) {
		String query = "SELECT * " +
					   "FROM tbl_book_copies " +
					   "WHERE bookId = ? AND branchId = ?";

		BookCopies copies = null;
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, bookId);
			statement.setInt(2, branchId);
			
			set = statement.executeQuery();
			
			if(!set.next()) {
				copies = new BookCopies(bookId, branchId, 0);
			} else {
				
				set.first();
				
				copies = new BookCopies(
						set.getInt("bookId"),
						set.getInt("branchId"),
						set.getInt("noOfCopies")
				);
			
				copies.setFound(true);
				
			}

		} catch (SQLException e) {
			System.out.println("Something wrong retrieving the book copies");
		} catch (NullPointerException e) {
			System.out.println("Result set Null");
		}

		return copies;
	}
	
	@Override
	public boolean update() {
		String query = "UPDATE tbl_book_copies " +
				   	   "SET bookId = ?, branchId = ?, noOfCopies = ? " +
				   	   "WHERE bookId = ? AND branchId = ?";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successful = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.bookId);
			statement.setInt(2, this.branchId);
			statement.setInt(3, this.noOfCopies);
			statement.setInt(4, this.bookId);
			statement.setInt(5, this.branchId);

			statement.executeUpdate();
			successful = true;
			conn.commit();
			
		} catch (SQLException e) {
			successful = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Could not close connection or statement");
				e.printStackTrace();
			}
		}

		return successful;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean save() {
		String query = "INSERT INTO tbl_book_copies " +
					   "VALUES(?, ?, ?) ";
		
		Connection conn = null;
		PreparedStatement statement = null;
		boolean successful = false;
		
		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.bookId);
			statement.setInt(2, this.branchId);
			statement.setInt(3, this.noOfCopies);
			
			statement.execute();
			successful = true;
			conn.commit();
			
		} catch (SQLException e) {
			successful = false;
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return successful;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public boolean wasFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	
}
