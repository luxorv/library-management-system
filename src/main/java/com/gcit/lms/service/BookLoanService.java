package com.gcit.lms.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.model.Book;
import com.gcit.lms.model.BookCopies;
import com.gcit.lms.model.BookLoan;
import com.gcit.lms.model.Borrower;
import com.gcit.lms.model.LibraryBranch;

public class BookLoanService {

	@Autowired
	BookLoanDAO bookLoanDao;
	
	@Autowired
	BookDAO bookDao;
	
	@Autowired
	BorrowerDAO borrowerDao;
	
	@Autowired
    BranchDAO branchDao;
	
	@Autowired
	BookCopiesDAO copiesDao;
	
	@Autowired
	BookService bookService;

	public void loanBook(BookLoan loan) {

		loan.setDueDate(BookLoan.dateFromNow(0));
		loan.setDateOut(BookLoan.dateFromNow(7));

		bookLoanDao.save(loan);
		this.reduceCopiesOnBook(loan);
	}

	public void updateLoan(BookLoan loan) {
		bookLoanDao.update(loan);
	}

	public void returnBook(BookLoan loan) {
		bookLoanDao.delete(loan);
		this.aumentCopiesOnBook(loan);
	}

	public BookLoan getLoan(BookLoan requestedLoan) {
		BookLoan loan = bookLoanDao.getLoan(requestedLoan);

		this.fillLoan(loan);

		return loan;
	}

	private BookLoan fillLoan(BookLoan loan) {
		Book book = bookDao.getBookWithID(loan.getBook());
		Borrower borrower = borrowerDao.getBorrowerWithCardNo(loan.getBorrower());
		LibraryBranch branch = branchDao.getBranchWithID(loan.getBranch());
		
		loan.setBook(book);
		loan.setBorrower(borrower);
		loan.setBranch(branch);
		
		return loan;
	}

	private void reduceCopiesOnBook(BookLoan loan) {
		BookCopies copy = copiesDao.getBookCopiesFromBranch(loan);
		
		copy.setNoOfCopies(copy.getNoOfCopies() - 1);
		
		copiesDao.update(copy);
	}

	private void aumentCopiesOnBook(BookLoan loan) {
		BookCopies copy = copiesDao.getBookCopiesFromBranch(loan);
		
		copy.setNoOfCopies(copy.getNoOfCopies() + 1);
		
		copiesDao.update(copy);
	}

	public ArrayList<BookLoan> getLoansFromBorrower(Borrower borrower) {
		ArrayList<BookLoan> loans = bookLoanDao.getLoansFromBorrower(borrower);

		for (BookLoan bookLoan : loans) {
			this.fillLoan(bookLoan);
		}

		return loans;
	}

	public ArrayList<BookLoan> getLoansFromBorrowerInBranch(Borrower borrower, LibraryBranch branch, String query, Integer pageNo) {
		ArrayList<BookLoan> loans = bookLoanDao.getLoansFromBorrowerInBranch(borrower, branch, query, pageNo);

		for (BookLoan bookLoan : loans) {
			this.fillLoan(bookLoan);
		}

		return loans;
	}

	public ArrayList<JSONObject> getLoanInfo(ArrayList<BookLoan> loans) {
		ArrayList<JSONObject>  loanInfo = new ArrayList<>();

		for(BookLoan loan: loans) {
			JSONObject loanJson = new JSONObject();

			loanJson.put("dueDate", loan.getDueDate().toString());
			loanJson.put("dateOut", loan.getDateOut().toString());

			JSONObject bookJson = new JSONObject();

			bookJson.put("title", loan.getBook().getTitle());
			bookJson.put("bookId", loan.getBook().getBookId());

			JSONObject borrowerJson = new JSONObject();

			borrowerJson.put("name", loan.getBorrower().getName());
			borrowerJson.put("cardNo", loan.getBorrower().getCardNo());

			JSONObject branchJson = new JSONObject();

			branchJson.put("name", loan.getBranch().getBranchName());
			branchJson.put("branchId", loan.getBranch().getBranchId());

			loanJson.put("book", bookJson);
			loanJson.put("branch", branchJson);
			loanJson.put("borrower", borrowerJson);

			loanInfo.add(loanJson);
		}

		return loanInfo;
	}

	public ArrayList<BookLoan> getAllLoans(BookLoan loan, String searchString) {
		ArrayList<BookLoan> loans = bookLoanDao.getAll(loan, searchString);
		
		for (BookLoan bookLoan : loans) {
			this.fillLoan(bookLoan);
		}
		
		return loans;
	}

	public ArrayList<BookLoan> getLoansFromBook(Book book) {
		return bookLoanDao.getLoansFromBook(book);
	}
}
