package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Author implements Savable {
	
	private int authorId;
	private String authorName;
	
	public Author(int authorId, String authorName) {
		this.setAuthorId(authorId);
		this.setAuthorName(authorName);
	}
	
	public Author(String authorName) {
		this.authorName = authorName;
	}
	
	public static ArrayList<Author> getAll() {
		String query = "SELECT * " +
					   "FROM tbl_author";
		
		ArrayList<Author> authors = new ArrayList<Author>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			set = statement.executeQuery();

			while(set.next()) {
				Author author = new Author(
						set.getInt("authorId"),
						set.getString("authorName")
				);

				authors.add(author);
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

		return authors;		
	}
	
	public static ArrayList<Author> getAuthorsForBook(int bookId) {
		String query = "SELECT * " +
					   "FROM tbl_book " +
					   "INNER JOIN tbl_book_author " +
					   "ON tbl_book.bookId = tbl_book_author.bookId " +
					   "INNER JOIN tbl_author " +
					   "ON tbl_book_author.authorId = tbl_author.authorId " +
					   "WHERE bookId = ?";

		ArrayList<Author> authors = new ArrayList<Author>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, bookId);
			
			set = statement.executeQuery();

			while(set.next()) {
				Author author = new Author(
						set.getInt("authorId"),
						set.getString("authorName")
				);

				authors.add(author);
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

		return authors;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save() {
		String query = "INSERT INTO tbl_author " +
					   "(authorName) " +
					   "VALUES(?)";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.authorName);

			statement.execute();
			successfull = true;
			
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to insert a new author");
			e.printStackTrace();
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

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}
