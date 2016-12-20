package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Book;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.Borrower;
import com.gcit.library.model.LibraryBranch;

public class BookLoanDAO extends BaseDAO {
	
	public Integer save(BookLoan loan) {
		return super.alter(
			QueryHelper.INSERT_LOAN,
			new Object[]{
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo(),
				loan.getDueDate(),
				loan.getDateOut()
			}
		);
	}
	
	public void update(BookLoan loan) {
		super.alter(
			QueryHelper.UPDATE_LOAN,
			new Object[]{
				loan.getDueDate(),
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo()
		});
	}
	
	public void delete(BookLoan loan) {
		super.alter(
			QueryHelper.DELETE_LOAN,
			new Object[]{
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo()
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BookLoan> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<BookLoan> loans = new ArrayList<BookLoan>();
		BookDAO bookDAO = new BookDAO();
		LibraryBranchDAO branchDAO = new LibraryBranchDAO();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		
		try {
			
			System.out.println(resultSet.getFetchSize());
			
			while(resultSet.next()) {
				BookLoan loan = new BookLoan();
				
				loan.setDueDate(resultSet.getDate("dueDate"));
				loan.setDateOut(resultSet.getDate("dateOut"));

				loan.setBook((Book) bookDAO.getAll(
					QueryHelper.BOOK_WITH_ID,
					false,
					new Object[]{resultSet.getInt("bookId")}
				).get(0));
					
				loan.setBranch((LibraryBranch) branchDAO.getAll(
					QueryHelper.BRANCH_WITH_ID,
					false,
					new Object[]{resultSet.getInt("branchId")}
				).get(0));
					
				loan.setBorrower((Borrower) borrowerDAO.getAll(
					QueryHelper.BORROWER_WITH_CARDNO,
					false,
					new Object[]{resultSet.getInt("cardNo")}
				).get(0));
				
				loans.add(loan);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
	}

}
