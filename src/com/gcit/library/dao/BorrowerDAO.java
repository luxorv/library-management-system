package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.Borrower;

public class BorrowerDAO extends BaseDAO {
	
	public Integer save(Borrower borrower) {
		return super.alter(
			QueryHelper.INSERT_BORROWER,
			new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone()}
		);
	}
	
	public void update(Borrower borrower) {
		super.alter(
			QueryHelper.UPDATE_BORROWER,
			new Object[]{
				borrower.getName(),
				borrower.getAddress(),
				borrower.getPhone(),
				borrower.getCardNo()
		});
	}
	
	public void delete(Borrower borrower) {
		super.alter(
			QueryHelper.DELETE_BORROWER,
			new Object[]{borrower.getCardNo()}
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Borrower> extractData(ResultSet resultSet, Boolean onlyBaseData) {
		ArrayList<Borrower> borrowers = null;
		
		try {
			borrowers = new ArrayList<Borrower>();
			
			while(resultSet.next()) {
				Borrower borrower = new Borrower();
				
				borrower.setCardNo(resultSet.getInt("cardNo"));
				borrower.setName(resultSet.getString("name"));
				borrower.setAddress(resultSet.getString("address"));
				borrower.setPhone(resultSet.getString("phone"));
				
				borrowers.add(borrower);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return borrowers;
	}
}
