package com.gcit.library.web;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.gcit.library.dao.BaseDAO;
import com.gcit.library.dao.BookLoanDAO;
import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.dao.QueryHelper;
import com.gcit.library.model.Book;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.Borrower;
import com.gcit.library.model.DatabaseManager;
import com.gcit.library.model.LibraryBranch;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({
	"/borrowers/pickborrower",
	"/borrowers/loan",
	"/admin/pickborrower",
	"/admin/shownewborrowerinfo",
	"/admin/showborrowerinfo",
	"/admin/addborrower",
	"/admin/editborrower",
	"/admin/deleteborrower",
	"/admin/updateloan",
	"/admin/pickborrowerforloans",
	"/admin/pickloans"
})
public class BorrowerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public String execute() {
		
		switch (this.getUrl()) {
			case "/borrowers/pickborrower":
				return this.listBorrowers();
			case "/admin/pickborrower":
				return this.listBorrowers();
			case "/admin/shownewborrowerinfo":
				return this.showBorrowerInfo();
			case "/admin/showborrowerinfo":
				return this.showBorrowerInfo();
			case "/admin/addborrower":
				return this.addBorrower();
			case "/admin/editborrower":
				return this.editBorrower();
			case "/admin/deleteborrower":
				return this.deleteBorrower();
			case "/borrowers/loan":
				return this.loanBook();
			case "/admin/pickborrowerforloans":
				return this.listBorrowers();
			case "/admin/pickloans":
				return this.listLoans();
			case "/admin/updateloan":
				return this.updateLoan();
		}
		
		return INTERNAL_SERVER_ERROR;
	}
	
	private String listLoans() {
		
		int cardNo = Integer.parseInt(req.getParameter("cardNo"));
		
		ArrayList<BookLoan> loans = new BookLoanDAO().getAll(
			QueryHelper.ALL_LOANS_FROM_BORROWER,
			false,
			new Object[]{cardNo}
		);
		
		System.out.println(loans);
		
		req.setAttribute("loans", loans);
		
		return "pickloans.jsp";
	}

	private String deleteBorrower() {
		
		int cardNo = Integer.parseInt(req.getParameter("cardNo"));
		
		Borrower borrower = new Borrower();
		
		borrower.setCardNo(cardNo);
		
		new BorrowerDAO().delete(borrower);
		
		return "pickborrower";
	}

	private String editBorrower() {
		
		int cardNo = Integer.parseInt(req.getParameter("cardNo"));
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		
		Borrower borrower = new Borrower();
		
		borrower.setCardNo(cardNo);
		
		if(name != null && name.length() > 0) {
			borrower.setName(name);
		}
		
		if(address != null && address.length() > 0) {
			borrower.setAddress(address);
		}
		
		if(phone != null && phone.length() > 0) {
			borrower.setPhone(phone);
		}
		
		new BorrowerDAO().update(borrower);
		
		return "pickborrower";
	}

	private String addBorrower() {
		
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		
		Borrower borrower = new Borrower();
		
		borrower.setName(name);
		borrower.setAddress(address);
		borrower.setPhone(phone);
		
		int cardNo = new BorrowerDAO().save(borrower);
		
		System.out.println("New Borrower cardNo: " + cardNo);
		
		return "pickborrower";
	}

	private String loanBook() {
    	
    	int branchId = Integer.parseInt(req.getParameter("branchId"));
    	int cardNo = Integer.parseInt(req.getParameter("cardNo"));
    	int bookId = Integer.parseInt(req.getParameter("bookId"));
    	int option = Integer.parseInt(req.getParameter("option"));
    	
    	BookLoan loan = new BookLoan();
    	Book book = new Book();
    	LibraryBranch branch = new LibraryBranch();
    	Borrower borrower = new Borrower();
    	
    	book.setBookId(bookId);
    	branch.setBranchId(branchId);
    	borrower.setCardNo(cardNo);
    	
    	loan.setBook(book);
    	loan.setBranch(branch);
    	loan.setBorrower(borrower);
    	
    	if(option == 1) {
    		loan.setDateOut(BookLoan.dateFromNow(0));
    		loan.setDueDate(BookLoan.dateFromNow(7));
    		
    		new BookLoanDAO().save(loan);
    	} else if(option == 2) {
    		new BookLoanDAO().delete(loan);
    	}
    	
    	return "menu.jsp";
	}
	
	private String showBorrowerInfo() {
		
		int cardNo;
		
		Borrower borrower = new Borrower();
		
		if(this.getUrl().equals("/admin/showborrowerinfo")) {
			
			cardNo = Integer.parseInt(req.getParameter("cardNo"));
			
			borrower = (Borrower) new BorrowerDAO().getAll(
				QueryHelper.BORROWER_WITH_CARDNO,
				false,
				new Object[]{cardNo}
			).get(0);
		} else {
			borrower.setName("");
			borrower.setAddress("");
			borrower.setPhone("");
		}
		
		req.setAttribute("borrower", borrower);
		
		return "updateborrower.jsp";
	}
	
	private String listBorrowers() {
		
		try {
			BaseDAO.setConnection(DatabaseManager.getInstance().getCurrentConnection());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		String forwardUrl = "pickborrower.jsp";
		
		if(this.getUrl().equals("/admin/pickborrowerforloans")) {
			forwardUrl = "pickborrowerforloans.jsp";
		}
		
		ArrayList<Borrower> borrowers = new BorrowerDAO().getAll(
			QueryHelper.ALL_BORROWERS,
			false,
			null
		);
		
		req.setAttribute("borrowers", borrowers);
		
		return forwardUrl;
	}
	
	private String updateLoan() {
		
		int cardNo = Integer.parseInt(req.getParameter("cardNo"));
		int branchId = Integer.parseInt(req.getParameter("branchId"));
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		String newDueDate = req.getParameter("dueDate");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new Date(System.currentTimeMillis());
		
		try {
			date = format.parse(newDueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		java.sql.Date dueDate = new Date(date.getTime()); 
		
		Book book = new Book();
		LibraryBranch branch = new LibraryBranch();
		Borrower borrower = new Borrower();
		
		book.setBookId(bookId);
		branch.setBranchId(branchId);
		borrower.setCardNo(cardNo);

		BookLoan loan = new BookLoan();
		
		loan.setBook(book);
		loan.setBranch(branch);
		loan.setBorrower(borrower);
		loan.setDueDate(dueDate);
		
		new BookLoanDAO().update(loan);
		
		return "pickloans";
	}

}
