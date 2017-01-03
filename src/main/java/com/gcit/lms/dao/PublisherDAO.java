package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<ArrayList<Publisher>>{
	
	public Integer save(Publisher publisher) {
		return template.update(
			QueryHelper.INSERT_PUBLISHER,
			new Object[]{
				publisher.getPublisherName(),
				publisher.getPublisherAddress(),
				publisher.getPublisherPhone()}
		);
	}
	
	public void update(Publisher publisher) {
		template.update(
			QueryHelper.UPDATE_PUBLISHER,
			new Object[]{
				publisher.getPublisherName(),
				publisher.getPublisherAddress(),
				publisher.getPublisherPhone(),
				publisher.getPublisherId()}
		);
	}
	
	public void delete(Publisher publisher) {
		template.update(
			QueryHelper.DELETE_PUBLISHER,
			new Object[]{publisher.getPublisherId()}
		);
	}
	
	public Publisher getPublisherWithID(Publisher publisher) {
		ArrayList<Publisher> publishers = template.query(
			QueryHelper.PUBLISHER_WITH_ID,
			new Object[]{publisher.getPublisherId()},
			this
		);
		
		if(publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		
		return null;
	}

	public ArrayList<Publisher> getAll(String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_PUBLISHERS_LIKE;

			this.setFetchSize(this.getQueryCount(sql, query));
			
			return template.query(
				sql,
				new Object[]{query},
				this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_PUBLISHERS;

			this.setFetchSize(this.getQueryCount(sql, query));

			return template.query(
				sql,
				this
			);
		}
	}
	

	@Override
	public ArrayList<Publisher> extractData(ResultSet resultSet) {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();

		try {
			while(resultSet.next()) {
				Publisher publisher = new Publisher();
				
				publisher.setPublisherId(resultSet.getInt("publisherId"));
				publisher.setPublisherName(resultSet.getString("publisherName"));
				publisher.setPublisherAddress(resultSet.getString("publisherAddress"));
				publisher.setPublisherPhone(resultSet.getString("publisherPhone"));

				publishers.add(publisher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}
}
