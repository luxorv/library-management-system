package com.gcit.library.web;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.GenreDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Book;
import com.gcit.library.model.DatabaseManager;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet({"/books", "/books/addBook", "/books/editBook", "book/deleteBook"})
public class BookServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2107981323203564206L;

	@Override
	public String execute() {
    	
    	System.out.println("Execute called");
    	String url = "ss";
    	System.out.println(url);
    	
    	switch (url) {
		case "addBook":
			return this.alterBook(true);
		case "editBook":
			return this.alterBook(false);
		case "deleteBook":
			return this.deleteBook();
		}
		return this.listBooks();
    }
    
	private String listBooks() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BookDAO bookDao = new BookDAO();
		ArrayList<Book> books = bookDao.getAll(
			QueryHelper.ALL_BOOKS,
			true,
			null
		);
		
		req.setAttribute("books", books);
		
		return "books/all.jsp";
	}

	public String alterBook(boolean isAdding) {

		req.setAttribute(
			"authors",
			new AuthorDAO().getAll(
				QueryHelper.ALL_AUTHORS,
				false,
				null
		));
		
		req.setAttribute(
				"genres",
				new GenreDAO().getAll(
					QueryHelper.ALL_GENRES,
					false,
					null
			));
		
		req.setAttribute("isAdding", isAdding);
		
		return "alterbook.jsp";
	}
	
	public String deleteBook() {
		
		Book book = new Book();
		
		book.setBookId(Integer.parseInt(req.getParameter("bookId")));
		
		new BookDAO().delete(book);
		
		return "all.jsp";
	}
}
