package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.model.Author;
import com.gcit.lms.model.Book;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<ArrayList<Author>>{

	
	public Integer save(Author author) {
		final String authorName = author.getAuthorName();
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
					QueryHelper.INSERT_AUTHOR,
					new String[] { "authorId" }
				);
				
				ps.setString(1, authorName);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void update(Author author) {
		template.update(
			QueryHelper.UPDATE_AUTHOR,
			new Object[]{
				author.getAuthorName(),
				author.getAuthorId()
			});
	}
	
	public void delete(Author author) {
		template.update(
			QueryHelper.DELETE_AUTHOR,
			new Object[]{author.getAuthorId()}
		);
	}
	
	public ArrayList<Author> getAll(String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%"+query+"%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_AUTHORS_LIKE;

			this.setFetchSize(this.getQueryCount(sql, query));
			
			return template.query(
				sql,
				new Object[]{query},
				this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_AUTHORS;

			this.setFetchSize(this.getQueryCount(sql, query));

			sql = this.getPagedSql(sql);

			return template.query(
				sql,
				this
			);
		}
	}

	public Author getAuthorWithID(Author author) {
		ArrayList<Author> authors =  template.query(
			QueryHelper.AUTHOR_WITH_ID,
			new Object[]{author.getAuthorId()},
			this
		);
		
		if(authors != null && !authors.isEmpty()){
			return authors.get(0);
		}
		
		return null;
	}

	public void relateAuthor(Author author) {
		for(Book book: author.getBooks()) {
			template.update(
					QueryHelper.RELATE_BOOK_WITH_AUTHOR,
					new Object[]{book.getBookId(), author.getAuthorId()}
			);
		}
	}

	public void unrelateAuthor(Author author) {
		template.update(
				QueryHelper.UNRELATE_AUTHOR_FROM_BOOKS,
				new Object[]{author.getAuthorId()}
		);
	}
	
	public ArrayList<Author> getAuthorsFromBook(Book book) {
		return template.query(
			QueryHelper.ALL_AUTHORS_FROM_BOOK,
			new Object[]{book.getBookId()},
			this
		);
	}

	@Override
	public ArrayList<Author> extractData(ResultSet resultSet) {
		ArrayList<Author> authors = new ArrayList<Author>();
		
		try {
			while(resultSet.next()){
				Author author = new Author();
				
				author.setAuthorId(resultSet.getInt("authorId"));
				author.setAuthorName(resultSet.getString("authorName"));
				
				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authors;
	}
}
