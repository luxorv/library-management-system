package com.gcit.library.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class BookLoan implements Savable {
	
	private int bookId;
	private int branchId;
	private int cardNo;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;
	
	public BookLoan(int bookId, int branchId, int cardNo) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateFromNow(0);
		this.dueDate = dateFromNow(7);
		this.dateIn = null;
	}
	
	public BookLoan(int bookId, int branchId, int cardNo, Date dateOut, Date dueDate) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateIn = null;
	}
	
	public static Date dateFromThen(Date then, int days) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(then);
		
		c.add(Calendar.DATE, days);
		
		Date newDate = new Date(c.getTime().getTime());
		
		return newDate;
	}
	
	public static Date dateFromNow(int days) {
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		
		Date newDate = new Date(c.getTime().getTime());
		
		return newDate;
	}
	
	public static ArrayList<BookLoan> getAllLoansFromBorrower(int cardNo) {
		String query = "SELECT * " +
					   "FROM tbl_book_loans " +
					   "WHERE cardNo = ?";

		ArrayList<BookLoan> loans = new ArrayList<BookLoan>();
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, cardNo);
			
			set = statement.executeQuery();

			while(set.next()) {
				BookLoan loan = new BookLoan(
					set.getInt("bookId"),
					set.getInt("branchId"),
					set.getInt("cardNo"),
					set.getDate("dateOut"),
					set.getDate("dueDate")
				);

				loans.add(loan);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseManager.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return loans;
	}

	public boolean update() {
		
		String query = "UPDATE tbl_book_loans "+
					   "SET bookId = ?, branchId = ?, cardNo = ?,"+
				       "dueDate = ?, dateOut = ? " +
					   "WHERE bookId = ? AND branchId = ? AND cardNo = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, this.bookId);
			statement.setInt(2, this.branchId);
			statement.setInt(3, this.cardNo);
			statement.setDate(4, this.dueDate);
			statement.setDate(5, this.dateOut);
			statement.setInt(6, this.bookId);
			statement.setInt(7, this.branchId);
			statement.setInt(8, this.cardNo);

			successful = statement.execute();
			conn.commit();

		} catch (SQLException e) {
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
		
		String query = "DELETE FROM tbl_book_loans "+
					   "WHERE bookId = ? AND branchId = ? AND cardNo = ?";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.bookId);
			statement.setInt(2, this.branchId);
			statement.setInt(3, this.cardNo);
			
			statement.execute();
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
		String query = "INSERT INTO tbl_book_loans " +
					   "(bookId, branchId, cardNo, dueDate, dateOut) " +
					   "VALUES (?, ?, ?, ?, ?)";
		
		boolean successful = false;
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			
			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);
			
			statement.setInt(1, this.bookId);
			statement.setInt(2, this.branchId);
			statement.setInt(3, this.cardNo);
			statement.setDate(4, this.dueDate);
			statement.setDate(5, this.dateOut);
			
			statement.execute();
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
				conn.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return successful;
	}
	
	public static void returnBook(BookLoan loan) {
		loan.delete();
	}
	
	public static BookLoan getMostRecentLoan(int bookId, int branchId, int cardNo) {
		String query = "SELECT * " +
					   "FROM tbl_book_loans " +
					   "WHERE branchId = ? AND cardNo = ? AND bookId = ?";

		BookLoan loan = null;
		Connection conn = null;
		PreparedStatement statement = null;

		try {

			conn = DatabaseManager.getInstance().getCurrentConnection();
			statement = conn.prepareStatement(query);

			statement.setInt(1, branchId);
			statement.setInt(2, cardNo);
			statement.setInt(3, bookId);

			ResultSet set = statement.executeQuery();
			
			while(set.next()) {
				loan = new BookLoan(
						set.getInt("bookId"),
						set.getInt("branchId"),
						set.getInt("cardNo")
				);
				
				loan.setDateOut(set.getDate("dateOut"));
				loan.setDueDate(set.getDate("dueDate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return loan;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDateIn() {
		return this.dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

}
