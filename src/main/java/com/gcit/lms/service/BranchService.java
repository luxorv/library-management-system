package com.gcit.lms.service;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.model.LibraryBranch;

public class BranchService {

	private String alert;
	
	@Autowired
	BranchDAO branchDao;
	
	public void createBranch(LibraryBranch branch) {
		branchDao.save(branch);
	}
	
	public void updateBranch(LibraryBranch branch) {
		branchDao.update(branch);
	}
	
	public void deleteBranch(LibraryBranch branch) {
		branchDao.delete(branch);
	}


	public ArrayList<JSONObject> getBranchesInfo(ArrayList<LibraryBranch> branches) {
		ArrayList<JSONObject> branchInfo = new ArrayList<>();

		for(LibraryBranch branch: branches) {

			JSONObject branchJson = new JSONObject();

			branchJson.put("branchName", branch.getBranchName());
			branchJson.put("branchAddress", branch.getBranchAddress());
			branchJson.put("branchId", branch.getBranchId());

			branchInfo.add(branchJson);
		}

		return branchInfo;
	}

	public LibraryBranch getBranch(LibraryBranch requestedBranch) {
		return branchDao.getBranchWithID(requestedBranch);
	}

	public Integer getBranchFetchSize() {
		return branchDao.getFetchSize();
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

	public ArrayList<LibraryBranch> getAllBranches(String query, Integer pageNo) {
		return branchDao.getAll(query, pageNo);
	}
}