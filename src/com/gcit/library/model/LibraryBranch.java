package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryBranch implements Savable {
	
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	public LibraryBranch(String branchName, String branchAddress) {
		this.setBranchName(branchName);
		this.setBranchAddress(branchAddress);
	}
	
	public static ArrayList<LibraryBranch> getAll() {
		
		String query = "SELECT * " +
						"FROM tbl_library_branch";
		
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ArrayList<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			set = statement.executeQuery();
			
			while(set.next()) {
				LibraryBranch branch = new LibraryBranch(
						set.getString("branchName"),
						set.getString("branchAddress")
				);
				
				branch.setBranchId(set.getInt("branchId"));
				branches.add(branch);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
			} catch (SQLException e) {
				System.out.println("Could not close");
				e.printStackTrace();
			}
		}
		
		return branches;
	}

	@Override
	public boolean update() {
		
		String query = "UPDATE tbl_library_branch " +
					   "SET branchName = ?, branchAddress = ? " +
					   "WHERE branchId = ?";
		
		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;
		
		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			System.out.println("New Name: " + this.branchName);
			System.out.println("New Address: " + this.branchAddress);
			System.out.println("BranchId: " + this.getBranchId());
			
			statement.setString(1, this.branchName);
			statement.setString(2, this.branchAddress);
			statement.setInt(3, this.branchId);
			
			statement.executeUpdate();
			successfull = true;
			conn.commit();
			
		} catch (SQLException e) {
			successfull = false;
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return successfull;
	}

	@Override
	public boolean delete() {
		String query = "DELETE FROM tbl_library_branch "+
					   "WHERE branchId = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.branchId);
			
			statement.execute();
			successful = true;
			conn.commit();
			
		} catch (SQLException e) {
			successful = false;
			System.out.println(successful);
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return successful;
	}

	@Override
	public boolean save() {
		String query = "INSERT INTO tbl_library_branch " +
					   "(branchName, branchAddress) " +
					   "VALUES(?, ?)";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.branchName);
			statement.setString(2, this.branchAddress);

			statement.execute();
			successfull = true;

			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to insert a new branch");
			successfull = false;
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e2) {
				System.out.println("Problems closing the connection");
			}
		}
		return successfull;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
}
