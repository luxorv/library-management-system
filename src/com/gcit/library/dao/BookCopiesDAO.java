package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Book;
import com.gcit.library.model.BookCopies;
import com.gcit.library.model.LibraryBranch;

public class BookCopiesDAO extends BaseDAO {
	
	public Integer save(BookCopies copies) {
		return super.alter(
			QueryHelper.INSERT_COPIES,
			new Object[]{copies.getBook().getBookId(), copies.getBranch().getBranchId()}
		);
	}
	
	public void update(BookCopies copies) {
		super.alter(
			QueryHelper.UPDATE_COPIES,
			new Object[]{copies.getNoOfCopies(), copies.getBook().getBookId(), copies.getBranch().getBranchId()}
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BookCopies> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<BookCopies> copies = null;
		BookDAO bookDAO = new BookDAO();
		LibraryBranchDAO branchDAO = new LibraryBranchDAO();
		
		try {
			copies = new ArrayList<BookCopies>();
			
			while(resultSet.next()) {
				BookCopies copy = new BookCopies();
				
				copy.setNoOfCopies(resultSet.getInt("noOfCopies"));
				
				copy.setBook((Book) bookDAO.getAll(
					QueryHelper.BOOK_WITH_ID,
					false,
					new Object[]{resultSet.getInt("bookId")}
				).get(0));
				
				copy.setBranch((LibraryBranch) branchDAO.getAll(
					QueryHelper.BRANCH_WITH_ID,
					false,
					new Object[]{resultSet.getInt("branchId")}
				).get(0));
				
				copies.add(copy);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return copies;
	}

}
