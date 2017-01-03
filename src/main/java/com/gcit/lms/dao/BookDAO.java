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
import com.gcit.lms.model.Borrower;
import com.gcit.lms.model.Genre;
import com.gcit.lms.model.LibraryBranch;
import com.gcit.lms.model.Publisher;

public class BookDAO extends BaseDAO implements ResultSetExtractor<ArrayList<Book>>{

	public Integer save(final Book book) {
		
		final String title = book.getTitle();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
					QueryHelper.INSERT_BOOK,
					new String[] { "bookId", "pubId" }
				);
				
				ps.setString(1, title);
				ps.setInt(2, book.getPublisher().getPublisherId());
				
				return ps;
			}
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	public void update(Book book) {
		template.update(
			QueryHelper.UPDATE_BOOK,
			new Object[]{
				book.getTitle(),
				book.getPublisher().getPublisherId(),
				book.getBookId()}
		);
	}

	public void delete(Book book) {
		template.update(
			QueryHelper.DELETE_BOOK,
			new Object[]{book.getBookId()}
		);
	}

	public ArrayList<Book> getBooksNotInBranch(LibraryBranch branch) {
		return template.query(
				QueryHelper.ALL_BOOKS_NOT_IN_BRANCH,
				new Object[]{branch.getBranchId()},
				this
		);
	}
	
	public ArrayList<Book> getBooksBy(String query, String queryType, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = this.getFilterQuery(queryType);

			this.setFetchSize(this.getQueryCount(sql, query));

			sql = this.getPagedSql(sql);

			return template.query(
				sql,
				new Object[]{query},
				this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BOOKS;

			this.setFetchSize(this.getQueryCount(sql, query));

			sql = this.getPagedSql(sql);

			return template.query(
				sql,
				this
			);
		}
	}

	private String getFilterQuery(String queryType) {

		switch (queryType) {
			case "Title":
				return QueryHelper.ALL_BOOKS_BY_TITLE;
			case "Author":
				return QueryHelper.ALL_BOOKS_BY_AUTHOR;
			case "Genre":
				return QueryHelper.ALL_BOOKS_BY_GENRE;
			case "Publisher":
				return  QueryHelper.ALL_BOOKS_BY_PUBLISHER;
		}

		return null;
	}
	
	public Book getBookWithID(Book book) {
		ArrayList<Book> books =  template.query(
			QueryHelper.BOOK_WITH_ID,
			new Object[]{book.getBookId()},
			this
		);
		
		if(books != null && !books.isEmpty()){
			return books.get(0);
		}
		
		return null;
	}
	
	public ArrayList<Book> getBooksFromAuthor(Author author) {
		return template.query(
			QueryHelper.ALL_BOOKS_FROM_AUTHOR,
			new Object[]{author.getAuthorId()},
			this
		);
	}
	
	public ArrayList<Book> getBooksFromGenre(Genre genre) {
		return template.query(
			QueryHelper.ALL_BOOKS_FROM_GENRE,
			new Object[]{genre.getGenreId()},
			this
		);
	}
	
	public ArrayList<Book> getBooksFromPublisher(Publisher publisher) {
		return template.query(
			QueryHelper.ALL_BOOKS_FROM_PUBLISHER,
			new Object[]{publisher.getPublisherId()},
			this
		);
	}
	
	public ArrayList<Book> getPossibleBooksForBorrower(Borrower borrower, LibraryBranch branch, String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BOOKS_NOT_LOANED_FROM_BRANCH_LIKE;

			this.setFetchSize(this.getQueryCount(
					QueryHelper.ALL_BOOKS_NOT_LOANED_FROM_BRANCH_LIKE_COUNT,
					borrower,
					branch,
					query
			));

			sql = this.getPagedSql(sql);

			return template.query(
					sql,
					new Object[]{query, branch.getBranchId(), borrower.getCardNo()},
					this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BOOKS_NOT_LOANED_FROM_BRANCH;

			this.setFetchSize(this.getQueryCount(
					QueryHelper.ALL_BOOKS_NOT_LOANED_FROM_BRANCH_COUNT,
					borrower,
					branch,
					query
			));

			sql = this.getPagedSql(sql);

			return template.query(
					sql,
					new Object[]{branch.getBranchId(), borrower.getCardNo()},
					this
			);
		}
	}

	protected Integer getQueryCount(String sql, Borrower borrower, LibraryBranch branch, String query) {

		if(query != null && !query.isEmpty()) {
			return template.queryForObject(
					sql,
					new Object[]{query, branch.getBranchId(), borrower.getCardNo()},
					Integer.class
			);
		} else {
			return template.queryForObject(
					sql,
					new Object[]{branch.getBranchId(), borrower.getCardNo()},
					Integer.class
			);
		}
	}
	
	public void relateBook(Book book) {
		
		for (Author author : book.getAuthors()) {
			template.update(
				QueryHelper.RELATE_BOOK_WITH_AUTHOR,
				new Object[]{book.getBookId(), author.getAuthorId()}
			);
		}
		
		for (Genre genre : book.getGenres()) {
			template.update(
				QueryHelper.RELATE_BOOK_WITH_GENRE,
				new Object[]{book.getBookId(), genre.getGenreId()}
			);
		}
	}
	
	public void unrelateBook(Book book) {
		
		template.update(
			QueryHelper.UNRELATE_BOOK_FROM_AUTHORS,
			new Object[]{book.getBookId()}
		);
		
		template.update(
			QueryHelper.UNRELATE_BOOK_FROM_GENRES,
			new Object[]{book.getBookId()}
		);
	}
	
	@Override
	public ArrayList<Book> extractData(ResultSet resultSet) {
		ArrayList<Book> books = new ArrayList<Book>();

		try {
			while(resultSet.next()){
				Book book = new Book();
				
				book.setBookId(resultSet.getInt("bookId"));
				book.setTitle(resultSet.getString("title"));

				Publisher publisher = new Publisher();
				
				publisher.setPublisherId(resultSet.getInt("pubId"));
				book.setPublisher(publisher);

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return books;
	}
}
