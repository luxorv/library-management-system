package com.gcit.library.controller;

import java.util.ArrayList;

import com.gcit.library.model.BookCopies;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.Borrower;
import com.gcit.library.model.LibraryBook;
import com.gcit.library.model.LibraryBranch;
import com.gcit.library.view.BookScreen;
import com.gcit.library.view.BorrowerScreen;
import com.gcit.library.view.LibrarianScreen;

public class BorrowerController implements Controller {

	private Borrower borrower;
	private BorrowerScreen borrowerScreen;
	private LibrarianScreen libScreen;
	
	private LibraryBranch selectedBranch;
	private BookScreen bookScreen;
	private LibraryBook selectedBook;
	private BookLoan bookLoan;
	
	private ArrayList<LibraryBranch> branches;
	private ArrayList<LibraryBook> booksInLibrary;
	
	private int indexOfSelectedBook;
	
	public BorrowerController() {

	}
	
	public void drawScreen() {
		this.borrowerScreen = (BorrowerScreen) ScreenManager.getInstance().getScreen("borrower"); 
		this.borrowerScreen.draw();
		
		this.borrower = Borrower.get(borrowerScreen.getCardNo());
		
		while(this.borrower == null) {
			borrowerScreen.getValidCardNo();
			this.borrower = Borrower.get(borrowerScreen.getCardNo());
		}
		
		this.drawBorrowerMenu();
	}
	
	public void drawBorrowerMenu() {
		borrowerScreen.drawBorrowerMenu();

		if(borrowerScreen.getMenuOption() == 3) {
			ScreenManager.getInstance().getScreen("main").draw();
		} else {
			this.fillScreenAndBranches();
			this.loanOrReturnABook();
		}
	}
	
	private void fillScreenAndBranches() {
		this.libScreen = (LibrarianScreen) ScreenManager.getInstance().getScreen("librarian");
		this.branches = LibraryBranch.getAll();
		this.libScreen.drawPickTheBranch(this.branches);
	}
	
	private void loanOrReturnABook() {
		int indexOfSelectedBranch = this.libScreen.getMenuOption();

		if(indexOfSelectedBranch > branches.size()) {
			this.drawBorrowerMenu();
		} else {

			this.selectedBranch = this.branches.get(indexOfSelectedBranch - 1);
			this.bookScreen = (BookScreen) ScreenManager.getInstance().getScreen("books");
			
			if(this.borrowerScreen.getMenuOption() == 1) {
				
				this.booksInLibrary = LibraryBook.getBooksNotLoanedFromBranch(
						this.borrower.getCardNo(),
						selectedBranch.getBranchId()
				);

				this.bookScreen.drawBooksOnBranch(this.booksInLibrary);
				this.pickBook();
				
				this.bookLoan = new BookLoan(
						this.selectedBook.getBookId(),
						this.selectedBranch.getBranchId(),
						this.borrower.getCardNo()
				);
				
				this.checkOutBook();
				
			} else if(borrowerScreen.getMenuOption() == 2) {
				
				this.booksInLibrary = LibraryBook.getBooksLoanedFromBranch(
						this.borrower.getCardNo(),
						selectedBranch.getBranchId()
				);

				this.bookScreen.drawBooksOnBranch(this.booksInLibrary);
				this.pickBook();
				
				this.bookLoan = BookLoan.getMostRecentLoan(
						this.selectedBook.getBookId(),
						this.selectedBranch.getBranchId(),
						this.borrower.getCardNo()
				);
	
				 this.returnBook();
			}			
		}
		this.drawBorrowerMenu();
	}
	
	private void pickBook() {
		this.indexOfSelectedBook = bookScreen.getMenuOption();
		
		if(this.indexOfSelectedBook > booksInLibrary.size()) {
			this.drawBorrowerMenu();
		} else {
			this.selectedBook = this.booksInLibrary.get(this.indexOfSelectedBook - 1);
		}
	}
	
	private void checkOutBook() {

		if(this.bookLoan.save()) {
			BookCopies copies = BookCopies.get(this.bookLoan.getBookId(), this.bookLoan.getBranchId());
			
			copies.setNoOfCopies(copies.getNoOfCopies() - 1);
			
			if(copies.update()) {
				System.out.println("Book checked out successfully");
			}
		} else {
			System.out.println("Something wrong checking out the book");
		}
	}
	
	private void returnBook() {
		BookLoan.returnBook(this.bookLoan);
		BookCopies copies = BookCopies.get(this.bookLoan.getBookId(), this.bookLoan.getBranchId());
		
		copies.setNoOfCopies(copies.getNoOfCopies() + 1);
		
		if(copies.update()) {
			System.out.println("Book returned");
		}
	}
}