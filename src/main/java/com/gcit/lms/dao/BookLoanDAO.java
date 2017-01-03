package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.lms.model.Book;
import com.gcit.lms.model.Borrower;
import com.gcit.lms.model.LibraryBranch;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.BookLoan;

public class BookLoanDAO extends BaseDAO implements ResultSetExtractor<ArrayList<BookLoan>>{
	
	public Integer save(BookLoan loan) {
		return template.update(
			QueryHelper.INSERT_LOAN,
			new Object[]{
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo(),
				loan.getDueDate(),
				loan.getDateOut()}
		);
	}
	
	public void update(BookLoan loan) {
		template.update(
			QueryHelper.UPDATE_LOAN,
			new Object[]{
				loan.getDueDate(),
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo()}
		);
	}

	public void delete(BookLoan loan) {
		template.update(
			QueryHelper.DELETE_LOAN,
			new Object[]{
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId(),
				loan.getBorrower().getCardNo()}
		);
	}

	public BookLoan getLoan(BookLoan loan) {
		ArrayList<BookLoan> loans = template.query(
				QueryHelper.LOAN_WITH_ID,
				new Object[]{
						loan.getBook().getBookId(),
						loan.getBranch().getBranchId(),
						loan.getBorrower().getCardNo()},
				this
		);

		if(loans != null && !loans.isEmpty()) {
			return loans.get(0);
		}

		return null;
	}

	public ArrayList<BookLoan> getLoansFromBorrower(Borrower borrower) {
		return template.query(
				QueryHelper.ALL_LOANS_FROM_BORROWER,
				new Object[]{borrower.getCardNo()},
				this
		);
	}

	public ArrayList<BookLoan> getLoansFromBorrowerInBranch(Borrower borrower, LibraryBranch branch, String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_LOANS_FROM_BORROWER_IN_BRANCH_LIKE;

			this.setFetchSize(this.getQueryCount(sql, borrower, branch, query));

			sql = this.getPagedSql(sql);

			return template.query(
					sql,
					new Object[]{query, branch.getBranchId(), borrower.getCardNo()},
					this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_LOANS_FROM_BORROWER_IN_BRANCH;

			this.setFetchSize(this.getQueryCount(sql, borrower, branch, query));

			sql = this.getPagedSql(sql);

			return template.query(
					sql,
					new Object[]{branch.getBranchId(), borrower.getCardNo()},
					this
			);
		}
	}

	protected Integer getQueryCount(String sql, Borrower borrower, LibraryBranch branch, String query) {

		String newSql = "SELECT count(*) " + sql.split("SELECT * ")[1].substring(1);

		if(query != null && !query.isEmpty()) {
			return template.queryForObject(
					newSql,
					new Object[]{query, branch.getBranchId(), borrower.getCardNo()},
					Integer.class
			);
		} else {
			return template.queryForObject(
					newSql,
					new Object[]{branch.getBranchId(), borrower.getCardNo()},
					Integer.class
			);
		}
	}


	public ArrayList<BookLoan> getAll(BookLoan loan, String searchString) {
		if(searchString != null && !searchString.isEmpty()) {
			searchString = "%"+searchString+"%";
			
			return template.query(
				QueryHelper.ALL_LOANS_LIKE,
				new Object[]{
					loan.getBorrower().getCardNo(),
					searchString},
				this
			);
		} else {
			return template.query(
				QueryHelper.ALL_LOANS_FROM_BORROWER,
				new Object[]{
					loan.getBorrower().getCardNo()},
				this
			);
		}
	}

	public ArrayList<BookLoan> getLoansFromBook(Book book) {
		return template.query(
				QueryHelper.ALL_LOANS_FROM_BOOK,
				new Object[]{book.getBookId()},
				this
		);
	}
	
	public Integer getCount() {
		return template.queryForObject(
			QueryHelper.LOAN_COUNT,
			Integer.class
		);
	}

	@Override
	public ArrayList<BookLoan> extractData(ResultSet resultSet) {
		ArrayList<BookLoan> loans = new ArrayList<BookLoan>();
		
		try {
			
			System.out.println(resultSet.getFetchSize());
			
			while(resultSet.next()) {
				BookLoan loan = new BookLoan();
				
				loan.setDueDate(resultSet.getDate("dueDate"));
				loan.setDateOut(resultSet.getDate("dateOut"));

				Book book = new Book();
				book.setBookId(resultSet.getInt("bookId"));
				loan.setBook(book);

				Borrower borrower = new Borrower();
				borrower.setCardNo(resultSet.getInt("cardNo"));
				loan.setBorrower(borrower);

				LibraryBranch branch = new LibraryBranch();
				branch.setBranchId(resultSet.getInt("branchId"));
				loan.setBranch(branch);
				
				loans.add(loan);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
	}

}
