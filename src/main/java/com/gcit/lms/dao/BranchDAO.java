package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.model.LibraryBranch;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<ArrayList<LibraryBranch>>{

	public Integer save(LibraryBranch branch) {
		return template.update(
			QueryHelper.INSERT_BRANCH,
			new Object[]{
				branch.getBranchName(),
				branch.getBranchAddress()}
		);
	}
	
	public void update(LibraryBranch branch) {
		template.update(
			QueryHelper.UPDATE_BRANCH,
			new Object[]{
				branch.getBranchName(),
				branch.getBranchAddress(),
				branch.getBranchId()}
		);
	}
	
	public void delete(LibraryBranch branch) {
		template.update(
			QueryHelper.DELETE_BRANCH,
			new Object[]{branch.getBranchId()}
		);
	}
	
	public LibraryBranch getBranchWithID(LibraryBranch branch) {
		ArrayList<LibraryBranch> branches = template.query(
			QueryHelper.BRANCH_WITH_ID,
			new Object[]{branch.getBranchId()},
			this
		);
		
		if(branches != null && !branches.isEmpty()) {
			return branches.get(0);
		}
		
		return null;
	}
	
	public ArrayList<LibraryBranch> getAll(String query, Integer pageNo) {
		if(query != null && !query.isEmpty()) {
			query = "%" + query + "%";

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BRANCHES_LIKE;

			this.setFetchSize(this.getQueryCount(sql, query));
			
			return template.query(
				sql,
				new Object[]{query},
				this
			);
		} else {

			this.setPageNo(pageNo);

			String sql = QueryHelper.ALL_BRANCHES;

			this.setFetchSize(this.getQueryCount(sql, query));

			return template.query(
				sql,
				this
			);
		}
	}
	
	@Override
	public ArrayList<LibraryBranch> extractData(ResultSet resultSet) {
		ArrayList<LibraryBranch> branches = null;
		
		try {
			branches = new ArrayList<LibraryBranch>();
			
			while(resultSet.next()) {
				LibraryBranch branch = new LibraryBranch();
				
				branch.setBranchId(resultSet.getInt("branchId"));
				branch.setBranchName(resultSet.getString("branchName"));
				branch.setBranchAddress(resultSet.getString("branchAddress"));
				
				branches.add(branch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return branches;
	}

	
}
