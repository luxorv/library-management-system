package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gcit.library.model.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO {

	public Integer save(LibraryBranch branch) {
		return super.alter(
			QueryHelper.INSERT_BRANCH,
			new Object[]{branch.getBranchName(), branch.getBranchAddress()}
		);
	}
	
	public void update(LibraryBranch branch) {
		super.alter(
			QueryHelper.UPDATE_BRANCH,
			new Object[]{branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()}
		);
	}
	
	public void delete(LibraryBranch branch) {
		super.alter(
			QueryHelper.DELETE_BRANCH,
			new Object[]{branch.getBranchId()}
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<LibraryBranch> extractData(ResultSet resultSet, Boolean onlyBaseData) {
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
