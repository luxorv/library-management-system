package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.Borrower;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<ArrayList<Borrower>> {
	
	public Integer save(Borrower borrower) {
		return template.update(
			QueryHelper.INSERT_BORROWER,
			new Object[]{
				borrower.getName(),
				borrower.getAddress(),
				borrower.getPhone()}
		);
	}
	
	public void update(Borrower borrower) {
		template.update(
			QueryHelper.UPDATE_BORROWER,
			new Object[]{
				borrower.getName(),
				borrower.getAddress(),
				borrower.getPhone(),
				borrower.getCardNo()}
		);
	}
	
	public void delete(Borrower borrower) {
		template.update(
			QueryHelper.DELETE_BORROWER,
			new Object[]{borrower.getCardNo()}
		);
	}
	
	public Borrower getBorrowerWithCardNo(Borrower borrower) {
		ArrayList<Borrower> borrowers = template.query(
			QueryHelper.BORROWER_WITH_CARDNO,
			new Object[]{borrower.getCardNo()},
			this
		);
		
		if(borrowers != null && borrowers.size() > 0) {
			return borrowers.get(0);
		}
		
		return null;
	}
	
	public ArrayList<Borrower> getAll(String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BORROWERS_LIKE;

			this.setFetchSize(this.getQueryCount(sql, query));

			return template.query(
					sql,
					new Object[]{query},
					this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BORROWERS;

			this.setFetchSize(this.getQueryCount(sql, query));
			return template.query(
				sql,
				this
			);
		}
	}

	@Override
	public ArrayList<Borrower> extractData(ResultSet resultSet) {
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
