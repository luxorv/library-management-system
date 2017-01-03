package com.gcit.lms.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.Genre;

public class GenreService {

	@Autowired
	GenreDAO genreDao;
	
	@Autowired
	BookDAO bookDao;
	
	public void createGenre(Genre genre) {
		genreDao.save(genre);
	}
	
	public void updateGenre(Genre genre) {
		genreDao.update(genre);
	}
	
	public void deleteGenre(Genre genre) {
		genreDao.delete(genre);
	}
	
	private Genre fillGenre(Genre genre) {
		ArrayList<Book> books = bookDao.getBooksFromGenre(genre);
		
		genre.setBooks(books);
		
		return genre;
	}
	
	public ArrayList<Genre> getAllGenres(String searchString) {
		ArrayList<Genre> genres = genreDao.getAll(searchString);
		
		for (Genre genre : genres) {
			genre = this.fillGenre(genre);
		}
		
		return genres;
	}
}
