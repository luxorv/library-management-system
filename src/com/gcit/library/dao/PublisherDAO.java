package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Publisher;

public class PublisherDAO extends BaseDAO {
	
	public Integer save(Publisher publisher) {
		return super.alter(
			QueryHelper.INSERT_PUBLISHER,
			new Object[]{
				publisher.getPublisherName(),
				publisher.getPublisherAddress(),
				publisher.getPublisherPhone()
		});
	}
	
	public void update(Publisher publisher) {
		super.alter(
			QueryHelper.UPDATE_PUBLISHER,
			new Object[]{
				publisher.getPublisherName(),
				publisher.getPublisherAddress(),
				publisher.getPublisherPhone(),
				publisher.getPublisherId()
		});
	}
	
	public void delete(Publisher publisher) {
		super.alter(
			QueryHelper.DELETE_PUBLISHER,
			new Object[]{publisher.getPublisherId()}
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Publisher> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		BookDAO bookDAO = new BookDAO();
		
		try {
			while(resultSet.next()) {
				Publisher publisher = new Publisher();
				
				publisher.setPublisherId(resultSet.getInt("publisherId"));
				publisher.setPublisherName(resultSet.getString("publisherName"));
				publisher.setPublisherAddress(resultSet.getString("publisherAddress"));
				publisher.setPublisherPhone(resultSet.getString("publisherPhone"));

				if(onlyBaseData) {
					publisher.setBooks(bookDAO.getAll(
						QueryHelper.ALL_BOOKS_FROM_PUBLISHER,
						false,
						new Object[]{publisher.getPublisherId()}
					));
				}
				publishers.add(publisher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}
}
