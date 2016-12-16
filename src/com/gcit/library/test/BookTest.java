package com.gcit.library.test;

import java.util.ArrayList;

import org.junit.Test;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.GenreDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Author;
import com.gcit.library.model.Book;
import com.gcit.library.model.Genre;
import com.gcit.library.model.Publisher;

public class BookTest {
	
	private Book testBook;

	@Test
	public void test() {
		// TODO: Fill test book.
	}
	
	@Test
	public void AddABook() {
		BookDAO bookDAO = new BookDAO();
		AuthorDAO authorDAO = new AuthorDAO();
		PublisherDAO pubDAO = new PublisherDAO();
		GenreDAO genreDAO = new GenreDAO();
		Book book = new Book();
		
		ArrayList<Author> authors = authorDAO.getAll(QueryHelper.ALL_AUTHORS, false, null);
		
		for (int i = 0; i < authors.size(); i++) {
			System.out.println(authors.get(i).toString());
		}
		
		ArrayList<Publisher> publishers = pubDAO.getAll(QueryHelper.ALL_PUBLISHERS, false, null);
		
		for (int i = 0; i < publishers.size(); i++) {
			System.out.println(publishers.get(i).toString());
		}
		
		ArrayList<Genre> genres = genreDAO.getAll(QueryHelper.ALL_GENRES, false, null);
		
		for (int i = 0; i < genres.size(); i++) {
			System.out.println(genres.get(i).toString());
		}
		
		book.setTitle(this.testBook.getTitle());
		book.setAuthors(this.testBook.getAuthors());
		book.setGenres(this.testBook.getGenres());
		book.setPublisher(this.testBook.getPublisher());
		
		int bookId = bookDAO.save(book);
		
		book.setBookId(bookId);
		
		bookDAO.relateBook(book);
		
		System.out.println(bookDAO.getAll(QueryHelper.BOOK_WITH_ID, true, new Object[]{bookId}).get(0));
		
		System.out.println(book.getPublisher().toString());
		authors = book.getAuthors();
		
		for (int i = 0; i < authors.size(); i++) {
			System.out.println(authors.get(i).toString());
		}
		
		genres = book.getGenres();
		
		for (int i = 0; i < genres.size(); i++) {
			System.out.println(genres.get(i).toString());
		}
	}
}
