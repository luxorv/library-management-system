package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Publisher implements Savable {
	
	private int publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	
	public Publisher(int publisherId, String publisherName, String publisherAddress, String publisherPhone) {
		this.setPublisherId(publisherId);
		this.setPublisherName(publisherName);
		this.setPublisherAddress(publisherAddress);
		this.setPublisherPhone(publisherPhone);
	}
	
	public Publisher(String publisherName, String publisherAddress, String publisherPhone) {
		this.setPublisherName(publisherName);
		this.setPublisherAddress(publisherAddress);
		this.setPublisherPhone(publisherPhone);
	}
	
	public static ArrayList<Publisher> getAll() {
		String query = "SELECT * " +
					   "FROM tbl_publisher";

		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			set = statement.executeQuery();

			while(set.next()) {
				Publisher publisher = new Publisher(
						set.getInt("publisherId"),
						set.getString("publisherName"),
						set.getString("publisherAddress"),
						set.getString("publisherPhone")
				);

				publishers.add(publisher);
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
		return publishers;
	}
	
	public static Publisher get(int pubId) {
		
		String query = "SELECT * " +
					   "FROM tbl_publisher " +
					   "WHERE publisherId = ?";
		
		Publisher pub = null;
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, pubId);
			set = statement.executeQuery();
			
			if(!set.next()) {
				return null;
			} else {
				set.first();
				
				pub = new Publisher(
					set.getInt("publisherId"),
					set.getString("publisherName"),
					set.getString("publisherAddress"),
					set.getString("publisherPhone")
				);
			}
			
		} catch (SQLException e) {
			System.out.println("Could not execute query to retrieve the publisher");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e2) {
				System.out.println("Problems closing the connection");
			}
		}
		return pub;
	}
	
	@Override
	public boolean save() {
		String query = "INSERT INTO tbl_publisher " +
					   "(publisherName, publisherAddress, publisherPhone) " +
				       "VALUES(?, ?, ?)";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.publisherName);
			statement.setString(2, this.publisherAddress);
			statement.setString(3, this.publisherPhone);

			statement.execute();
			successfull = true;

			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to insert new publisher");
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

	@Override
	public boolean delete() {
		String query = "DELETE FROM tbl_publisher "+
					   "WHERE publisherId = ?";

		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, this.publisherId);

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
	public boolean update() {
		String query = "UPDATE tbl_publisher "+
					   "SET publisherName = ?, publisherAddress = ?, publisherPhone = ? " +
					   "WHERE publisherId = ?";

		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.publisherName);
			statement.setString(2, this.publisherAddress);
			statement.setString(3, this.publisherPhone);
			statement.setInt(4, this.publisherId);

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

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherAddress() {
		return publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

}
