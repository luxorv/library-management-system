package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Genre;

public class GenreDAO extends BaseDAO {
	
	public Integer save(Genre genre) {
		return super.alter(
			QueryHelper.INSERT_GENRE,
			new Object[]{genre.getGenreName()}
		);
	}
	
	public void update(Genre genre) {
		super.alter(
			QueryHelper.UPDATE_GENRE,
			new Object[]{genre.getGenreId()}
		);
	}
	
	public void delete(Genre genre) {
		super.alter(
			QueryHelper.DELETE_GENRE,
			new Object[]{genre.getGenreId()}
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Genre> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		BookDAO bookDAO = new BookDAO();
		
		try {
			while(resultSet.next()) {
				Genre genre = new Genre();
				
				genre.setGenreId(resultSet.getInt("genre_id"));
				genre.setGenreName(resultSet.getString("genre_name"));

				if(onlyBaseData) {
					genre.setBooks(bookDAO.getAll(
						QueryHelper.ALL_BOOKS_FROM_GENRE,
						!onlyBaseData,
						new Object[]{genre.getGenreId()}
					));
				}
				genres.add(genre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}
}
