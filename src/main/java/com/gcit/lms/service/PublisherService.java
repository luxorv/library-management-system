package com.gcit.lms.service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.Publisher;

public class PublisherService {

	private String alert;

	@Autowired
	PublisherDAO publisherDao;
	
	@Autowired
	BookDAO bookDao;
	
	public void createPublisher(Publisher publisher) {
		publisherDao.save(publisher);
	}
	
	public void updatePublisher(Publisher publisher) {
		publisherDao.update(publisher);
	}
	
	public void deletePublisher(Publisher publisher) {
		publisherDao.delete(publisher);
	}
	
	private Publisher fillPublisher(Publisher publisher) {
		ArrayList<Book> books = bookDao.getBooksFromPublisher(publisher);
		
		publisher.setBooks(books);
		
		return publisher;
	}

	public Publisher getPublisher(Publisher publisher) {
		return publisherDao.getPublisherWithID(publisher);
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {

		if(alert.equals("Good")) {
			this.alert = "";
		} else if(alert.equals("Bad")) {
			this.alert = "";
		}
	}

	public Integer getPublisherFetchSize() {
		return publisherDao.getFetchSize();
	}

	public ArrayList<JSONObject> getPublishersInfo(ArrayList<Publisher> publishers) {
		ArrayList<JSONObject> pubInfo = new ArrayList<>();

		for (Publisher publisher: publishers) {

			JSONObject pubJson = new JSONObject();

			pubJson.put("publisherName", publisher.getPublisherName());
			pubJson.put("publisherAddress", publisher.getPublisherAddress());
			pubJson.put("publisherPhone", publisher.getPublisherPhone());
			pubJson.put("publisherId", publisher.getPublisherId());

			JSONArray books = new JSONArray();
			for(Book book: publisher.getBooks()) {
				JSONArray bookInfo = new JSONArray();

				bookInfo.add(book.getBookId());
				bookInfo.add(book.getTitle());

				books.add(bookInfo);
			}

			pubJson.put("books", books);
			pubInfo.add(pubJson);
		}

		return pubInfo;
	}
	
	public ArrayList<Publisher> getAllPublishers(String query, Integer pageNo) {
		ArrayList<Publisher> publishers = publisherDao.getAll(query, pageNo);
		
		for (Publisher publisher : publishers) {
			this.fillPublisher(publisher);
		}
		
		return publishers;
	}
}
