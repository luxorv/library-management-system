package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.lms.model.Book;
import com.gcit.lms.model.LibraryBranch;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.BookCopies;
import com.gcit.lms.model.BookLoan;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<ArrayList<BookCopies>>{
	
	public Integer save(BookCopies copies) {
		return template.update(
			QueryHelper.INSERT_COPIES,
			new Object[]{
				copies.getBook().getBookId(),
				copies.getBranch().getBranchId(),
				copies.getNoOfCopies()}
		);
	}
	
	public void update(BookCopies copies) {
		template.update(
			QueryHelper.UPDATE_COPIES,
			new Object[]{
				copies.getNoOfCopies(),
				copies.getBook().getBookId(),
				copies.getBranch().getBranchId()}
		);
	}

	public BookCopies getBookCopy(BookCopies copy) {
		ArrayList<BookCopies> copies = template.query(
				QueryHelper.COPY_WITH_ID,
				new Object[]{
						copy.getBranch().getBranchId(),
						copy.getBook().getBookId()},
				this
		);

		if(copies != null && !copies.isEmpty()) {
			return copies.get(0);
		}

		return null;
	}

	
	public ArrayList<BookCopies> getAll(BookCopies copy, String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_COPIES_LIKE;

			this.setFetchSize(this.getQueryCount(sql, copy.getBranch().getBranchId(), query));

			return template.query(
					sql,
					new Object[]{
						copy.getBranch().getBranchId(),
						query},
					this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_COPIES_IN_BRANCH;

			this.setFetchSize(this.getQueryCount(sql, copy.getBranch().getBranchId(), query));

			return template.query(
					sql,
					new Object[]{copy.getBranch().getBranchId()},
					this
			);
		}
	}

	private Integer getQueryCount(String sql, Integer branchId, String query) {

		String newSql = "SELECT count(*) " + sql.split("SELECT * ")[1].substring(1);

		if(query != null && !query.isEmpty()) {
			return template.queryForObject(
					newSql,
					new Object[]{
							branchId,
							query},
					Integer.class
			);
		} else {
			return template.queryForObject(
					newSql,
					new Object[]{branchId},
					Integer.class
			);
		}
	}
	
	public BookCopies getBookCopiesFromBranch(BookLoan loan) {
		ArrayList<BookCopies> copies = template.query(
			QueryHelper.ALL_COPIES_OF_BOOK_FROM_BRANCH,
			new Object[]{
				loan.getBook().getBookId(),
				loan.getBranch().getBranchId()},
			this
		);
		
		if(copies != null && !copies.isEmpty()) {
			return copies.get(0);
		}
		
		return null;
	}
	
	@Override
	public ArrayList<BookCopies> extractData(ResultSet resultSet) {
		ArrayList<BookCopies> copies = null;
		
		try {
			copies = new ArrayList<>();
			
			while(resultSet.next()) {
				BookCopies copy = new BookCopies();

				copy.setNoOfCopies(resultSet.getInt("noOfCopies"));

				LibraryBranch branch = new LibraryBranch();
				branch.setBranchId(resultSet.getInt("branchId"));

				copy.setBranch(branch);

				Book book = new Book();
				book.setBookId(resultSet.getInt("bookId"));

				copy.setBook(book);

				copies.add(copy);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return copies;
	}

}
