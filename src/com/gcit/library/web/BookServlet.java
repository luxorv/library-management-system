package com.gcit.library.web;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.BookCopiesDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.GenreDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Author;
import com.gcit.library.model.Book;
import com.gcit.library.model.BookCopies;
import com.gcit.library.model.DatabaseManager;
import com.gcit.library.model.Genre;
import com.gcit.library.model.LibraryBranch;
import com.gcit.library.model.Publisher;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet({
	"/admin/pickbook",
	"/admin/addbook",
	"/admin/updatebook",
	"/admin/deletebook",
	"/admin/showbookinfo",
	"/admin/shownewbookinfo",
	"/borrowers/pickbookforloan",
	"/branches/pickbookforcopies",
	"/branches/updatecopy",
	"/admin/pagebooks"
})
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
    }

    @Override
	public String execute() {
    	
    	try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	switch (this.getUrl()) {
    		case "/admin/pickbook":
    			return this.listBooks();
    		case "/admin/pagebooks":
    			return this.pageBooks();
    		case "/borrowers/pickbookforloan":
    			return this.listBooksToBorrower();
    		case "/admin/showbookinfo":
    			return this.showBookInfo();
    		case "/admin/shownewbookinfo":
    			return this.showBookInfo();
    		case "/admin/deletebook":
    			return this.deleteBook();
    		case "/admin/addbook":
    			return this.saveBook(true);
    		case "/admin/updatebook":
    			return this.editBook();
    		case "/branches/pickbookforcopies":
    			return this.listBooksInBranch();
    		case "/branches/updatecopy":
    			return this.updateCopy();
		}
    	
    	return PAGE_NOT_FOUND;
    }
    
	private String pageBooks() {
		
		int pageNo = Integer.parseInt(req.getParameter("pageNo"));
		
		BookDAO bookDao = new BookDAO();
		
		bookDao.setPageNo(pageNo);
		
		ArrayList<Book> books = bookDao.getAll(
			QueryHelper.ALL_BOOKS,
			false,
			null
		);
		
		int count = bookDao.getCount(
			QueryHelper.BOOK_COUNT,
			null
		);
		
		req.setAttribute("books", books);
		req.setAttribute("count", count);
		req.setAttribute("pageNo", pageNo);
		req.setAttribute("pageSize", bookDao.getPageSize());
		
		return "pickbook.jsp";
	}

	private String editBook() {
		
		return this.saveBook(false);
	}

	private String updateCopy() {
		
		int branchId = Integer.parseInt(req.getParameter("branchId"));
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		int noOfCopies = Integer.parseInt(req.getParameter("noOfCopies"));
		
		BookCopies copy = new BookCopies();
		Book book = new Book();
		LibraryBranch branch = new LibraryBranch();
		
		book.setBookId(bookId);
		branch.setBranchId(branchId);
		
		copy.setBook(book);
		copy.setBranch(branch);
		copy.setNoOfCopies(noOfCopies);
		
		BookCopiesDAO bookCopiesDao = new BookCopiesDAO();
		
		ArrayList<BookCopies> oldCopies = bookCopiesDao.getAll(
			QueryHelper.ALL_COPIES_OF_BOOK_FROM_BRANCH, 
			false,
			new Object[]{bookId, branchId}
		);
		
		if(oldCopies != null && oldCopies.size() > 0) {
			bookCopiesDao.update(copy);
		} else {
			bookCopiesDao.save(copy);
		}
		
		return "pickbookforcopies?branchId=" + branchId;
	}

	private String saveBook(boolean isAdding) {
    	
    	Book book = new Book();
    	
    	String title = req.getParameter("title");
    	String[] authorIds = req.getParameterValues("authors");
    	String[] genreIds = req.getParameterValues("genres");
    	String pubId = req.getParameter("pubId");
    	
    	if(authorIds != null && authorIds.length > 0) {
    		
    		ArrayList<Author> authors = new ArrayList<Author>();
    		
    		for (String authorId : authorIds) {
    			Author author = new Author();
    			author.setAuthorId(Integer.parseInt(authorId));
    			authors.add(author);
			}
    		
    		book.setAuthors(authors);
    	}
    	
    	if(genreIds != null && genreIds.length > 0) {
    		ArrayList<Genre> genres = new ArrayList<Genre>();
    		
    		for (String genreId : genreIds) {
    			Genre genre = new Genre();
    			genre.setGenreId(Integer.parseInt(genreId));
    			genres.add(genre);
			}
    		
    		book.setGenres(genres);
    	}
    	
    	if(pubId != null && pubId.length() > 0) {
    		Publisher publisher = new Publisher();
    		
    		publisher.setPublisherId(Integer.parseInt(pubId));
    		
    		book.setPublisher(publisher);
    	}
    	
    	book.setTitle(title);
    	
    	BookDAO bookDao = new BookDAO();
    	Book oldBook = new Book();
    	int bookId = -1;
    	
    	if(!isAdding) {
    		
    		bookId = Integer.parseInt(req.getParameter("bookId"));
    		
    		oldBook = (Book) bookDao.getAll(
    			QueryHelper.BOOK_WITH_ID,
    			true,
    			new Object[]{bookId}
    		).get(0);
    		
    		if(!oldBook.getTitle().equals(title)) {
    			oldBook.setTitle(title);
    		}
    		
    		if(oldBook.getPublisher().getPublisherId() != book.getPublisher().getPublisherId()) {
    			oldBook.setPublisher(book.getPublisher());
    		}
    		
    		bookDao.unrelateBook(oldBook);
    		
    		oldBook.setAuthors(book.getAuthors());
    		oldBook.setGenres(book.getGenres());
    		
    		bookDao.relateBook(oldBook);
    		
    		bookDao.update(oldBook);
    		
    	} else {
    		
    		bookId = bookDao.save(book);
    		book.setBookId(bookId);
    		bookDao.relateBook(book);
    	}
    	
    	return "pagebooks?pageNo=1";
    }

    private String listBooksInBranch() {

    	int branchId = Integer.parseInt(req.getParameter("branchId"));
    	
    	LibraryBranch branch = new LibraryBranch();
    	
    	branch.setBranchId(branchId);
    	
    	ArrayList<Book> books = new BookDAO().getAll(
    		QueryHelper.ALL_BOOKS,
    		false,
    		null
    	);

    	ArrayList<BookCopies> copies = new BookCopiesDAO().getAll(
    		QueryHelper.ALL_COPIES_IN_BRANCH,
    		true,
    		new Object[]{branchId}
    	);
    	
    	for (Book book : books) {
    		BookCopies tempCopy = new BookCopies();
    		
    		tempCopy.setBook(book);
    		tempCopy.setBranch(branch);
    		
			if(!copies.contains(tempCopy)) {
				BookCopies copy = new BookCopies();
				
				copy.setBook(book);
				copy.setBranch(branch);
				copy.setNoOfCopies(0);
				
				copies.add(copy);
			}
		}
    	
    	req.setAttribute("copies", copies);

    	return "pickbookforcopies.jsp";
    }
    
    private String listBooksToBorrower() {

    	int branchId = Integer.parseInt(req.getParameter("branchId"));
    	int cardNo = Integer.parseInt(req.getParameter("cardNo"));
    	int option = Integer.parseInt(req.getParameter("option"));
    	
    	System.out.println("option on books: " + option);
    	
    	ArrayList<Book> books = null;
    	
    	if(option == 1) {
    		
    		books = new BookDAO().getAll(
    			QueryHelper.ALL_BOOKS_NOT_LOANED_FROM_BRANCH,
    	    	false,
    	    	new Object[]{branchId, cardNo}
    	    );
    	} else if(option == 2) {
    		books = new BookDAO().getAll(
    				QueryHelper.ALL_BOOKS_LOANED_FROM_BRANCH,
        	    	false,
        	    	new Object[]{cardNo, branchId}
        	);
    	}
    	
    	System.out.println("branchId: " + branchId + " cardNo: " + cardNo);
    	
    	req.setAttribute("books", books);
    	req.setAttribute("cardNo", cardNo);
    	req.setAttribute("branchId", branchId);
    	req.setAttribute("option", option);
    	
    	return "pickbookforloan.jsp";
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
		
		return "pickbook.jsp";
	}

	private String showBookInfo() {

		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int bookId;
		
		Book book = new Book();
		
		if(this.getUrl().equals("/admin/showbookinfo")) {
		
			bookId = Integer.parseInt(req.getParameter("bookId"));
			
			book = (Book) new BookDAO().getAll(
				QueryHelper.BOOK_WITH_ID, 
				true,
				new Object[] {bookId}
			).get(0);
			
		} else {
			book.setTitle("");
		}
		
		req.setAttribute("publishers",
			new PublisherDAO().getAll(
				QueryHelper.ALL_PUBLISHERS,
				false,
				null
		));
		
		req.setAttribute("authors",
			new AuthorDAO().getAll(
				QueryHelper.ALL_AUTHORS,
				false,
				null
		));
		
		req.setAttribute("genres",
			new GenreDAO().getAll(
				QueryHelper.ALL_GENRES,
				false,
				null
		));
		
		req.setAttribute("book", book);
		
		return "updatebook.jsp";
	}
	
	private String deleteBook() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Book book = new Book();
		
		book.setBookId(Integer.parseInt(req.getParameter("bookId")));
		
		new BookDAO().delete(book);
		
		return "all.jsp";
	}
}
