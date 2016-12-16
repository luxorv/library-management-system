package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Author;

public class AuthorDAO extends BaseDAO {

	public Integer save(Author author) {
		return super.alter(
			QueryHelper.INSERT_AUTHOR,
			new Object[]{author.getAuthorName()}
		);
	}
	
	public void update(Author author) {
		super.alter(
			QueryHelper.UPDATE_AUTHOR,
			new Object[]{author.getAuthorId()}
		);
	}
	
	public void delete(Author author) {
		super.alter(
			QueryHelper.DELETE_AUTHOR,
			new Object[]{author.getAuthorId()}
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Author> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<Author> authors = new ArrayList<Author>();
		BookDAO bookDAO = new BookDAO();
		
		try {
			while(resultSet.next()) {
				Author author = new Author();
				
				author.setAuthorId(resultSet.getInt("authorId"));
				author.setAuthorName(resultSet.getString("authorName"));

				if(onlyBaseData) {
					author.setBooks(bookDAO.getAll(
						QueryHelper.ALL_BOOKS_FROM_AUTHOR,
						!onlyBaseData,
						new Object[]{author.getAuthorId()}
					));
				}
				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authors;
	}

}
