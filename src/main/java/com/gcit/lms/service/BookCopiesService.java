package com.gcit.lms.service;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.BookCopies;
import com.gcit.lms.model.LibraryBranch;

public class BookCopiesService {

	private String alert;

	@Autowired
	BookCopiesDAO bookCopiesDao;
	
	@Autowired
	BookDAO bookDao;
	
	@Autowired
    BranchDAO branchDao;
	
	public void createCopies(BookCopies copy) {
		bookCopiesDao.save(copy);
	}
	
	public void updateCopies(BookCopies copy) {
		bookCopiesDao.update(copy);
	}

	public BookCopies fillCopy(BookCopies copy) {
		Book book = bookDao.getBookWithID(copy.getBook());
		LibraryBranch branch = branchDao.getBranchWithID(copy.getBranch());
		
		copy.setBook(book);
		copy.setBranch(branch);
		
		return copy;
	}

	public BookCopies getBookCopies(BookCopies requestedCopy) {
		BookCopies copy = bookCopiesDao.getBookCopy(requestedCopy);

		if(copy != null) {
			this.fillCopy(copy);
		}

		return copy;
	}

	public ArrayList<BookCopies> getAllCopiesFromBranch(BookCopies copy, String query, Integer pageNo) {
		ArrayList<BookCopies> copies = bookCopiesDao.getAll(copy, query, pageNo);
		
		for (BookCopies bookCopies : copies) {
			this.fillCopy(bookCopies);
		}
		
		return copies;
	}

	public ArrayList<JSONObject> getCopiesInfo(ArrayList<BookCopies> copies) {
		JSONArray copiesInfo = new JSONArray();

		for (BookCopies copy: copies) {
			JSONObject copyJson = new JSONObject();

			JSONArray bookInfo = new JSONArray();

			bookInfo.add(copy.getBook().getBookId());
			bookInfo.add(copy.getBook().getTitle());

			copyJson.put("book", bookInfo);

			JSONArray branchInfo = new JSONArray();

			branchInfo.add(copy.getBranch().getBranchId());
			branchInfo.add(copy.getBranch().getBranchName());

			copyJson.put("branch", branchInfo);
			copyJson.put("noOfCopies", copy.getNoOfCopies());

			copiesInfo.add(copyJson);
		}

		return copiesInfo;
	}

	public Integer getCopiesFetchSize() {
		return bookCopiesDao.getFetchSize();
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {

		if(alert.equals("Good")) {
			this.alert = "";
		} else if(alert.equals("Bad"))
		this.alert = alert;
	}
}
