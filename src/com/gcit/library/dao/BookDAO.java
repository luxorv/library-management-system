package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Author;
import com.gcit.library.model.Book;
import com.gcit.library.model.Genre;
import com.gcit.library.model.Publisher;

public final class BookDAO extends BaseDAO {

	public Integer save(Book book) {
		return super.alter(
			QueryHelper.INSERT_BOOK,
			new Object[]{book.getTitle(), book.getPublisher().getPublisherId()}
		);
	}
	
	public void update(Book book) {
		super.alter(
			QueryHelper.UPDATE_BOOK,
			new Object[]{book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()}
		);
	}
	
	public void delete(Book book) {
		super.alter(
			QueryHelper.DELETE_BOOK,
			new Object[]{book.getBookId()}
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Book> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		
		ArrayList<Book> books = new ArrayList<Book>();
		Publisher publisher = new Publisher();
		AuthorDAO authorDAO = new AuthorDAO();
		GenreDAO genreDAO = new GenreDAO();
		
		try {
			while(resultSet.next()) {
				Book book = new Book();
				
				book.setBookId(resultSet.getInt("bookId"));
				book.setTitle(resultSet.getString("title"));
				
				publisher.setPublisherId(resultSet.getInt("pubId"));
				
				book.setPublisher(publisher);

				if(onlyBaseData) {
					book.setAuthors(authorDAO.getAll(
						QueryHelper.ALL_AUTHORS_FROM_BOOK,
						!onlyBaseData,
						new Object[]{book.getBookId()}
					));
					
					book.setGenres(genreDAO.getAll(
						QueryHelper.ALL_GENRES_FROM_BOOK,
						!onlyBaseData,
						new Object[]{book.getBookId()}
					));
				}
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public void relateBook(Book book) {
		
		for (Author author : book.getAuthors()) {
			super.alter(
				QueryHelper.RELATE_BOOK_WITH_AUTHOR,
				new Object[]{book.getBookId(), author.getAuthorId()}
			);
		}
		
		for (Genre genre : book.getGenres()) {
			super.alter(
				QueryHelper.RELATE_BOOK_WITH_GENRE,
				new Object[]{book.getBookId(), genre.getGenreId()}
			);
		}
	}
}
