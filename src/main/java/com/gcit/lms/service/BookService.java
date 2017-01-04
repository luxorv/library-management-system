package com.gcit.lms.service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.model.Author;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.Borrower;
import com.gcit.lms.model.Genre;
import com.gcit.lms.model.LibraryBranch;
import com.gcit.lms.model.Publisher;

public class BookService {

	private String alert;
	
	@Autowired
	private BookDAO bookDao;
	
	@Autowired
	private AuthorDAO authorDao;
	
	@Autowired
	private GenreDAO genreDao;
	
	@Autowired
	private PublisherDAO publisherDao;

	@Transactional
	public void createBook(Book book) {
		Integer bookId = bookDao.save(book);
		book.setBookId(bookId);
		bookDao.relateBook(book);
	}
	
	@Transactional
	public void updateBook(Book book) {
		bookDao.update(book);
		bookDao.unrelateBook(book);
		bookDao.relateBook(book);
	}
	
	public void deleteBook(Book book) {
		bookDao.delete(book);
	}
	
	private void fillBook(Book book) {
		ArrayList<Author> authors = authorDao.getAuthorsFromBook(book);
		ArrayList<Genre> genres = genreDao.getGenresFromBook(book);
		
		Publisher publisher = publisherDao.getPublisherWithID(book.getPublisher());
		
		book.setAuthors(authors);
		book.setGenres(genres);
		book.setPublisher(publisher);
	}
	
	public Book getBook(Book requestedBook) {
		Book book = bookDao.getBookWithID(requestedBook);

		if(book != null) {
			this.fillBook(book);
		}

		return book;
	}

	public ArrayList<Book> getBooksFromAuthor(Author author) {
		return bookDao.getBooksFromAuthor(author);
	}
	
	public ArrayList<Book> getPossibleLoansFromBorrower(Borrower borrower, LibraryBranch branch, String query, Integer pageNo) {
		ArrayList<Book> books = bookDao.getPossibleBooksForBorrower(borrower, branch, query, pageNo);
		
		for (Book book : books) {
			this.fillBook(book);
		}
		
		return books;
	}
	
	public ArrayList<Book> getBooksBy(String query, String queryTipe, Integer pageNo) {

		ArrayList<Book> books = bookDao.getBooksBy(query, queryTipe, pageNo);
		
		for (Book book : books) {
			this.fillBook(book);
		}
		
		return books;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alertType) {

		if(alertType.equals("Good")) {

			this.alert = "<div class='alert alert-success alert-dismissible' role='alert'>" +
					"<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
					"<span aria-hidden='true'>&times;</span></button>" +
					"Great! Operation successful </div>";

		} else if(alertType.equals("Bad")) {
			this.alert = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
					"<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
					"<span aria-hidden='true'>&times;</span></button>" +
					"Ohh Snap! You can't delete a book with a loan! </div>";
		} else {
			this.alert = null;
		}
	}

	public ArrayList<Book> getBooksNotInBranch(LibraryBranch branch) {
		return bookDao.getBooksNotInBranch(branch);
	}

	public Integer getBookFetchSize() {
		return bookDao.getFetchSize();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JSONObject> getBooksInfo(ArrayList<Book> books) {
		
		ArrayList<JSONObject> bookInfo = new ArrayList<JSONObject>();
		
		for(Book book : books) {
			
			JSONObject bookJson = new JSONObject();
			
			bookJson.put("title", book.getTitle());
			bookJson.put("bookId", book.getBookId());
			
			JSONArray authors = new JSONArray();
			
			for(Author author : book.getAuthors()) {
				JSONArray authorInfo = new JSONArray();
				
				authorInfo.add(author.getAuthorId());
				authorInfo.add(author.getAuthorName());
				
				authors.add(authorInfo);
			}
			
			bookJson.put("authors", authors);
			
			JSONArray genres = new JSONArray();
			
			for(Genre genre : book.getGenres()) {
				JSONArray genreInfo = new JSONArray();
				
				genreInfo.add(genre.getGenreId());
				genreInfo.add(genre.getGenreName());
				
				genres.add(genreInfo);
			}
			
			bookJson.put("genres", genres);
			
			JSONArray pubInfo = new JSONArray();
			
			pubInfo.add(book.getPublisher().getPublisherId());
			pubInfo.add(book.getPublisher().getPublisherName());
			
			bookJson.put("publisher", pubInfo);
			
			bookInfo.add(bookJson);
		}
		
		return bookInfo;
	}
}
