package com.gcit.library.service;

import java.sql.SQLException;

import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Book;

public class AdminService extends BaseService {
	
	public static Integer addBook(Book book) {
		
		BookDAO bookDao = new BookDAO();
		
		int bookId = bookDao.save(book);
		
		book.setBookId(bookId);
		bookDao.relateBook(book);
		
		return bookId;
	}

	public static Book bookWithID(int bookId) throws SQLException {
		
		startConnection();
		
		BookDAO bookDao = new BookDAO();
		
		Book book = (Book) bookDao.getAll(
			QueryHelper.BOOK_WITH_ID,
			true,
			new Object[]{bookId}
		).get(0);
		
		closeConnection();
		
		return book;
	}
}