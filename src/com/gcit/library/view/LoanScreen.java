package com.gcit.library.view;

import java.sql.Date;
import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.LibraryBook;

public class LoanScreen implements Screen {

	private int menuOption;
	private Date dueDate;
	
	@Override
	public void draw() {
		
	}
	
	public void drawPickALoan(ArrayList<BookLoan> loans, ArrayList<LibraryBook> books) {
		System.out.println();
		for (int i = 0; i < loans.size() && i < books.size(); i++) {
			BookLoan loan = loans.get(i);
			LibraryBook book = books.get(i);
			
			System.out.println((i + 1) + ") " + book.getTitle() + " " + loan.getDueDate());
		}
		
		System.out.println((loans.size() + 1) + ") Quit to Previous\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}

	public boolean updateLoanDueDate(BookLoan loan) {
		System.out.print("\nYou have chosen to update the due date of Loan with due date: ");
		System.out.println(loan.getDueDate() + "\n");
		
		System.out.print("How many days would you like to add? (Enter 0 to cancel): ");
		
		int days = InputRetriever.getInstance().getScanner().nextInt();
		
		if(days == 0) {
			return false;
		}
		
		this.dueDate = BookLoan.dateFromThen(loan.getDueDate(), days);
		
		return true;
	}

	public Date getNewDueDate() {
		return this.dueDate;
	}

	public int getMenuOption() {
		return this.menuOption;
	}
}
