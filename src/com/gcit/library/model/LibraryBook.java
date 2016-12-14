package com.gcit.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryBook implements Savable {
	
	private int bookId;
	private int pubId;
	private String title;
	
	public LibraryBook() {
		
	}
	
	public LibraryBook(int bookId) {
		this.bookId = bookId;
	}
	
	public LibraryBook(String title, int pubId) {
		this.title = title;
		this.pubId = pubId;
	}
	
	public LibraryBook(int bookId, String title) {
		this.bookId = bookId;
		this.title = title;
	}
	
	public LibraryBook(int bookId, String title, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.pubId = pubId;
	}
	
	public static ArrayList<LibraryBook> getAll() {
		String query = "SELECT * " +
					   "FROM tbl_book";
		
		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			set = statement.executeQuery();
			
			while(set.next()) {
				LibraryBook book = new LibraryBook(
						set.getInt("bookId"),
						set.getString("title")
				);
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return books;
	}
	
	public static ArrayList<LibraryBook> getBooksWithCopiesInBranch(int branchId) {
		String query = "SELECT * " +
					   "FROM tbl_book " +
					   "INNER JOIN tbl_book_copies " +
					   "ON tbl_book_copies.bookId = tbl_book.bookId " +
					   "INNER JOIN tbl_library_branch " +
					   "ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
					   "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?";

		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, branchId);
			
			set = statement.executeQuery();

			while(set.next()) {
				LibraryBook book = new LibraryBook(
						set.getInt("bookId"),
						set.getString("title")
				);

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(set);
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return books;
	}
	
	public static ArrayList<LibraryBook> getBooksLoanedFromBranch(int cardNo, int branchId) {
		String query = "SELECT * " +
					   "FROM tbl_book_loans " +
					   "INNER JOIN tbl_book " +
					   "ON tbl_book.bookId = tbl_book_loans.bookId " +
					   "WHERE tbl_book_loans.cardNo = ? AND tbl_book_loans.branchId = ? AND tbl_book_loans.dateIn IS NULL";

		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, cardNo);
			statement.setInt(2, branchId);
			
			set = statement.executeQuery();
			
			while(set.next()) {
				LibraryBook book = new LibraryBook(
						set.getInt("bookId"),
						set.getString("title")
				);

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(set);
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}
	
	public static ArrayList<LibraryBook> getBooksLoanedFromBorrower(int cardNo) {
		String query = "SELECT * " +
					   "FROM tbl_book " +
					   "INNER JOIN tbl_book_loans " +
					   "ON tbl_book_loans.bookId = tbl_book.bookId " +
					   "WHERE tbl_book_loans.cardNo = ?";

		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, cardNo);

			set = statement.executeQuery();

			while(set.next()) {
				LibraryBook book = new LibraryBook(
						set.getInt("bookId"),
						set.getString("title")
				);

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}
	
	public static ArrayList<LibraryBook> getBooksNotLoanedFromBranch(int cardNo, int branchId) {
		String query = "SELECT tbl_book.title, tbl_book.bookId, tbl_library_branch.branchId " +
					   "FROM tbl_book " +
					   "INNER JOIN tbl_book_copies " +
					   "ON tbl_book_copies.bookId = tbl_book.bookId " +
					   "INNER JOIN tbl_library_branch " +
					   "ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
					   "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ? AND tbl_book.bookId NOT IN ( " +
					   "SELECT bl.bookId " +
					   "FROM tbl_book_loans bl " +
					   "WHERE bl.cardNo = ?)";

		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, branchId);
			statement.setInt(2, cardNo);
			
			set = statement.executeQuery();
			
			while(set.next()) {
				LibraryBook book = new LibraryBook(
						set.getInt("bookId"),
						set.getString("title")
				);

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(set);
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}
	
	@Override
	public boolean update() {
		String query = "UPDATE tbl_book "+
					   "SET title = ? " +
					   "WHERE bookId = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setString(1, this.title);
			statement.setInt(2, this.bookId);
			
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
		String query = "DELETE FROM tbl_book "+
					   "WHERE bookId = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.bookId);
			
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

	
	public boolean relateBookWithAuthor(int authorId) {
		String query = "INSERT INTO tbl_book_authors " +
					   "(bookId, authorId) " +
				       "VALUES(?, ?)";

		ArrayList<LibraryBook> books = LibraryBook.getAll();
		this.bookId = books.get(books.size() - 1).getBookId();
		
		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, this.bookId);
			statement.setInt(2, authorId);

			statement.execute();
			successfull = true;

			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to relate book with author");
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

	@Override
	public boolean save() {
		String query = "INSERT INTO tbl_book " +
					   "(title, pubId) " +
					   "VALUES(?, ?)";

		Connection conn = null;
		PreparedStatement statement = null;
		boolean successfull = false;

		try {
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setString(1, this.title);
			statement.setInt(2, this.pubId);

			statement.execute();
			successfull = true;
			
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Could not execute query to insert a new book");
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

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
}
