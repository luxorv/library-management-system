package com.gcit.lms.service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.model.Author;
import com.gcit.lms.model.Book;
import org.springframework.transaction.annotation.Transactional;

public class AuthorService {

	private String alert;

	@Autowired
	private BookDAO bookDao;
	
	@Autowired
	private AuthorDAO authorDao;

	@Transactional
	public void createAuthor(Author author) {
		Integer authorId = authorDao.save(author);
		author.setAuthorId(authorId);
		authorDao.relateAuthor(author);
	}

	@Transactional
	public void updateAuthor(Author author) {
		authorDao.update(author);
		authorDao.unrelateAuthor(author);
		authorDao.relateAuthor(author);
	}
	
	public void deleteAuthor(Author author) {
		authorDao.delete(author);
	}
	
	private Author fillAuthor(Author author) {
		ArrayList<Book> books = bookDao.getBooksFromAuthor(author);

		author.setBooks(books);
		
		return author;
	}

	public Author getAuthor(Author requestedAuthor) {
		Author author = authorDao.getAuthorWithID(requestedAuthor);

		if(author != null) {
			this.fillAuthor(author);
		}

		return author;
	}

	public Integer getAuthorFetchSize() {
		return authorDao.getFetchSize();
	}

	public ArrayList<JSONObject> getAuthorsInfo(ArrayList<Author> authors) {

		ArrayList<JSONObject> authorInfo = new ArrayList<>();

		for (Author author: authors) {
			JSONObject authorJson = new JSONObject();

			authorJson.put("authorName", author.getAuthorName());
			authorJson.put("authorId", author.getAuthorId());

			JSONArray books = new JSONArray();

			for(Book book: author.getBooks()) {
				JSONArray bookInfo = new JSONArray();

				bookInfo.add(book.getBookId());
				bookInfo.add(book.getTitle());

				books.add(bookInfo);
			}

			authorJson.put("books", books);
			authorInfo.add(authorJson);
		}

		return authorInfo;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public ArrayList<Author> getAllAuthors(String query, Integer pageNo) {
		
		ArrayList<Author> authors = authorDao.getAll(query, pageNo);
		
		for (Author author : authors) {
			this.fillAuthor(author);
		}
		
		return authors;
	}
}
