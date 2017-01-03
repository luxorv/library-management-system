package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.Book;
import com.gcit.lms.model.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<ArrayList<Genre>>{
	
	public Integer save(Genre genre) {
		return template.update(
			QueryHelper.INSERT_GENRE,
			new Object[]{genre.getGenreName()},
			this
		);
	}
	
	public void update(Genre genre) {
		template.update(
			QueryHelper.UPDATE_GENRE,
			new Object[]{genre.getGenreId()},
			this
		);
	}
	
	public void delete(Genre genre) {
		template.update(
			QueryHelper.DELETE_GENRE,
			new Object[]{genre.getGenreId()},
			this
		);
	}
	
	public ArrayList<Genre> getAll(String searchString) {
		if(searchString != null && !searchString.isEmpty()) {
			searchString = "%" + searchString + "%";
			
			return template.query(
				QueryHelper.ALL_GENRES_LIKE,
				new Object[]{searchString},
				this
			);
		} else {
			return template.query(
				QueryHelper.ALL_GENRES,
				this
			);
		}
	}
	
	public ArrayList<Genre> getGenresFromBook(Book book) {
		return template.query(
			QueryHelper.ALL_GENRES_FROM_BOOK,
			new Object[]{book.getBookId()},
			this
		);
	}
	
	public Integer getCount() {
		return template.queryForObject(
			QueryHelper.GENRE_COUNT,
			Integer.class
		);
	}

	@Override
	public ArrayList<Genre> extractData(ResultSet resultSet) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		
		try {
			while(resultSet.next()) {
				Genre genre = new Genre();
				
				genre.setGenreId(resultSet.getInt("genre_id"));
				genre.setGenreName(resultSet.getString("genre_name"));

				genres.add(genre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}
}
