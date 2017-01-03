package com.gcit.lms.service;

import java.util.ArrayList;

import com.gcit.lms.model.Book;
import com.gcit.lms.model.Publisher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.model.Borrower;

public class BorrowerService {

	private String alert;

	@Autowired
	BorrowerDAO borrowerDao;
	
	public void createBorrower(Borrower borrower) {
		borrowerDao.save(borrower);
	}
	
	public void updateBorrower(Borrower borrower) {
		borrowerDao.update(borrower);
	}
	
	public void deleteBorrower(Borrower borrower) {
		borrowerDao.delete(borrower);
	}
	
	public boolean validateBorrower(Borrower borrower) {
		Borrower borr = borrowerDao.getBorrowerWithCardNo(borrower);
		
		return borr != null;
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

	public Borrower getBorrower(Borrower borrower) {
		return borrowerDao.getBorrowerWithCardNo(borrower);
	}

	public Integer getBorrowerFetchSize() {
		return borrowerDao.getFetchSize();
	}

	public ArrayList<JSONObject> getBorrowersInfo(ArrayList<Borrower> borrowers) {
		ArrayList<JSONObject> borrowerInfo = new ArrayList<>();

		for (Borrower borrower: borrowers) {

			JSONObject borrowerJson = new JSONObject();

			borrowerJson.put("borrowerName", borrower.getName());
			borrowerJson.put("borrowerAddress", borrower.getAddress());
			borrowerJson.put("borrowerPhone", borrower.getPhone());
			borrowerJson.put("cardNo", borrower.getCardNo());

			borrowerInfo.add(borrowerJson);
		}

		return borrowerInfo;
	}
	
	public ArrayList<Borrower> getAllBorrowers(String query, Integer pageNo) {
		return borrowerDao.getAll(query, pageNo);
	}
}
