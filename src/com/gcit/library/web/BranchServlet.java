package com.gcit.library.web;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.LibraryBranchDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.DatabaseManager;
import com.gcit.library.model.LibraryBranch;

@WebServlet({
	"/branches",
	"/branches/pickbranch",
	"/admin/pickbranch",
	"/branches/addbranch", 
	"/branches/editbranch",
	"/admin/deletebranch",
	"/admin/addbranch",
	"/branches/update",
	"/branches/showinfo",
	"/borrowers/pickbranchforloan",
	"/admin/shownewbranchinfo",
	"/admin/updatebranch",
	"/admin/showbranchinfo"
})
public class BranchServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784381096079609630L;

	@Override
	public String execute() {
		
		switch (this.getUrl()) {
			case "/branches/pickbranch":
				return this.listBranches(true);
			case "/admin/pickbranch":
				return this.listBranches(false);
			case "/borrowers/pickbranchforloan":
				return this.listBranches(true);
			case "/branches":
				return this.listBranches(false);
			case "/admin/addbranch":
				return this.addBranch();
			case "/branches/showinfo":
				return this.showBranchInfo();
			case "/admin/shownewbranchinfo":
				return this.showBranchInfo();
			case "/admin/showbranchinfo":
				return this.showBranchInfo();
			case "/admin/updatebranch":
				return this.editBranch();
			case "/branches/update":
				return this.editBranch();
			case "/admin/deletebranch":
				return this.deleteBranch();
			case "/admin/savebranch":
				return this.saveBranch();
		}
		
		return PAGE_NOT_FOUND;
	}
	
	private String editBranch() {
		int branchId = Integer.parseInt(req.getParameter("branchId"));
		String branchName = req.getParameter("branchName");
		String branchAddress = req.getParameter("branchAddress");
		
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchId(branchId);
		
		if(branchName != null && branchName.length() > 0) {
			branch.setBranchName(branchName);
		}
		
		if(branchAddress != null && branchAddress.length() > 0) {
			branch.setBranchAddress(branchAddress);
		}
		
		new LibraryBranchDAO().update(branch);
		
		return "pickbranch";
	}

	private String listBranches(boolean isPicking) {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String forwardUrl = "";
		
		ArrayList<LibraryBranch> branches = new LibraryBranchDAO().getAll(
			QueryHelper.ALL_BRANCHES,
			false,
			null
		);
		
		if(req.getParameter("option") != null) {
			int option = Integer.parseInt(req.getParameter("option"));
			req.setAttribute("option", option);
		}
		
		if(req.getParameter("cardNo") != null) {
			int cardNo = Integer.parseInt(req.getParameter("cardNo"));
			req.setAttribute("cardNo", cardNo);
			forwardUrl = "pickbranchforloan.jsp";
		} else {
			forwardUrl = "pickbranch.jsp";
		}
		
		if(req.getParameter("user") != null) {
			forwardUrl = "pickbranch.jsp";
		}
		
		req.setAttribute("branches", branches);
		
		return forwardUrl;
	}
	
	private String addBranch() {
		
		String branchName = req.getParameter("branchName");
		String branchAddress = req.getParameter("branchAddress");
		
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		
		int branchId = new LibraryBranchDAO().save(branch);
		
		System.out.println("New Branch Id: " + branchId);
		
		return "pickbranch";
	}
	
	private String showBranchInfo() {
		
		int branchId;
		
		LibraryBranch branch = new LibraryBranch();
		
		if(this.getUrl().equals("/branches/showinfo") || this.getUrl().equals("/admin/showbranchinfo")){
			branchId = Integer.parseInt(req.getParameter("branchId"));
		
			branch = (LibraryBranch) new LibraryBranchDAO().getAll(
				QueryHelper.BRANCH_WITH_ID,
				false,
				new Object[]{branchId}
				).get(0);
		} else {
			branch.setBranchName("");
			branch.setBranchAddress("");
		}
		
		System.out.println(branch);
		
		req.setAttribute("branch", branch);
		
		return "updatebranch.jsp";
	}
	
	private String deleteBranch() {
		int branchId = Integer.parseInt(req.getParameter("branchId"));
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchId(branchId);
		
		new LibraryBranchDAO().delete(branch);
		
		return "pickbranch";
	}
	
	private String saveBranch() {
		String branchName = req.getParameter("branchName");
		String branchAddress = req.getParameter("branchAddress");
		
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		
		new LibraryBranchDAO().save(branch);
		
		return "pickbranch";
	}

}
