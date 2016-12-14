package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Borrower implements Savable {

	private int cardNo;
	private String name;
	private String address;
	private String phone;
	
	public Borrower(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public Borrower(int cardNo, String name, String address, String phone) {
		this.cardNo = cardNo;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public Borrower(int cardNo) {
		this.cardNo = cardNo;
	}
	
	public static ArrayList<Borrower> getAll() {
		String query = "SELECT * " +
					   "FROM tbl_borrower";

		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			set = statement.executeQuery();

			while(set.next()) {
				Borrower borrower = new Borrower(
					set.getInt("cardNo"),
					set.getString("name"),
					set.getString("address"),
					set.getString("phone")
				);

				borrowers.add(borrower);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return borrowers;
	}
	
	public static Borrower get(int cardNo) {
		String query = "SELECT * " +
						"FROM tbl_borrower " +
						"WHERE cardNo = ?";
		
		Borrower borrower = null;
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, cardNo);
			
			set = statement.executeQuery();
			
			while(set.next()) {
				borrower = new Borrower(
					set.getInt("cardNo"),
					set.getString("name"),
					set.getString("address"),
					set.getString("phone")
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Result set Null");
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return borrower;
	}

	@Override
	public boolean update() {
		String query = "UPDATE tbl_borrower "+
					   "SET name = ?, address = ?, phone = ? " +
					   "WHERE cardNo = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setString(1, this.name);
			statement.setString(2, this.address);
			statement.setString(3, this.phone);
			statement.setInt(4, this.cardNo);
			
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
	public boolean delete() {
		String query = "DELETE FROM tbl_borrower "+
					   "WHERE cardNo = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.cardNo);
			
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
		String query = "INSERT INTO tbl_borrower " +
					   "(name, address, phone) " +
					   "VALUES(?, ?, ?)";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.name);
			statement.setString(2, this.address);
			statement.setString(3, this.phone);

			statement.execute();
			successfull = true;

			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to insert a new borrower");
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

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
