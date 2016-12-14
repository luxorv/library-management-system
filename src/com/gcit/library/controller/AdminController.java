package com.gcit.library.controller;

import java.sql.Date;
import java.util.ArrayList;

import com.gcit.library.model.Author;
import com.gcit.library.model.BookLoan;
import com.gcit.library.model.Borrower;
import com.gcit.library.model.LibraryBook;
import com.gcit.library.model.LibraryBranch;
import com.gcit.library.model.Publisher;
import com.gcit.library.view.AdminScreen;
import com.gcit.library.view.AuthorScreen;
import com.gcit.library.view.BookScreen;
import com.gcit.library.view.BorrowerScreen;
import com.gcit.library.view.LibrarianScreen;
import com.gcit.library.view.LoanScreen;
import com.gcit.library.view.PublisherScreen;

public class AdminController implements Controller {

	private AdminScreen adminScreen;
	private BookScreen bookScreen;
	private AuthorScreen authorScreen;
	private PublisherScreen publisherScreen;
	private LibrarianScreen libScreen;
	private BorrowerScreen borrowerScreen;
	private LoanScreen loanScreen;
	
	private ArrayList<LibraryBook> books;
	private ArrayList<Author> authors;
	private ArrayList<LibraryBranch> branches;
	private ArrayList<Publisher> publishers;
	private ArrayList<Borrower> borrowers;
	private ArrayList<BookLoan> loans;
	
	private LibraryBook selectedBook;
	private Publisher selectedPublisher;
	private LibraryBranch selectedBranch;
	private Borrower selectedBorrower;
	private BookLoan selectedLoan;
	
	public AdminController() {
		books = null;
		authors = null;
		publishers = null;
		branches = null;
		borrowers = null;
		loans = null;
	}
	
	public void drawScreen() {
		this.adminScreen = (AdminScreen) ScreenManager.getInstance().getScreen("admin");
		this.adminScreen.draw();
		
		if(this.adminScreen.getMenuOption() == 1) {
			this.bookScreen = (BookScreen) ScreenManager.getInstance().getScreen("books");
			this.authorScreen = (AuthorScreen) ScreenManager.getInstance().getScreen("author");
			this.drawEditBookAndAuthor();
		} else if(this.adminScreen.getMenuOption() == 2) {
			this.publisherScreen = (PublisherScreen) ScreenManager.getInstance().getScreen("publisher");
			this.drawEditPublisher();
		} else if(this.adminScreen.getMenuOption() == 3) {
			this.libScreen = (LibrarianScreen) ScreenManager.getInstance().getScreen("librarian");
			this.drawEditLibraryBranch();
		} else if(this.adminScreen.getMenuOption() == 4) {
			this.borrowerScreen = (BorrowerScreen) ScreenManager.getInstance().getScreen("borrower");
			this.drawEditBorrower();
		} else if(this.adminScreen.getMenuOption() == 5) {
			this.borrowerScreen = (BorrowerScreen) ScreenManager.getInstance().getScreen("borrower");
			this.loanScreen = (LoanScreen) ScreenManager.getInstance().getScreen("loan");
			this.drawEditDueDateForLoan();
		} else if(this.adminScreen.getMenuOption() == 6) {
			ScreenManager.getInstance().getScreen("main").draw();
		}
	}

	private void drawEditBookAndAuthor() {
		this.adminScreen.drawAddUpdateOrDelete("Book");
		
		if(this.adminScreen.getMenuOption() == 1) {
			this.addNewBook();
		} else if(this.adminScreen.getMenuOption() == 4) {
			this.drawScreen();
		} else {
			this.editBook();
		}
	}
	
	private void addNewBook() {
		if(this.bookScreen.drawAddOrUpdateABook()) {
			Publisher pub = Publisher.get(Integer.valueOf(this.bookScreen.getBookInfo().get(1)));

			if(pub == null) {
				this.adminScreen.drawPublisherNotValid();
			} else {

				LibraryBook newBook = new LibraryBook(
					this.bookScreen.getBookInfo().get(0),
					Integer.valueOf(this.bookScreen.getBookInfo().get(1))
				);
				
				this.authorScreen.drawAuthorMenu();
				
				if(this.authorScreen.getMenuOption() == 1) {
					this.authors = Author.getAll();
					this.authorScreen.drawPickAnAuthor(authors);
					
					if(this.authorScreen.getMenuOption() <= authors.size()) {
						Author author = authors.get(this.authorScreen.getMenuOption() - 1);
						newBook.save();
						newBook.relateBookWithAuthor(author.getAuthorId());
					}
				} else if(this.authorScreen.getMenuOption() == 2) {
					this.authorScreen.drawNewAuthor();
					Author author = new Author(this.authorScreen.getNewAuthorName());
					author.save();
					
					authors = Author.getAll();
					author = authors.get(authors.size() - 1);
					
					newBook.save();
					newBook.relateBookWithAuthor(author.getAuthorId());
				}
			}
		}
		this.drawScreen();
	}
	
	private void editBook() {
		this.books = LibraryBook.getAll();
		this.bookScreen.drawPickABook(books);
		
		if(this.bookScreen.getMenuOption() > this.books.size()) {
			this.drawScreen();
		} else {
			this.selectedBook = this.books.get(this.bookScreen.getMenuOption() - 1);
			
			if(this.adminScreen.getMenuOption() == 2) {
				this.updateBook();
			} else if(this.adminScreen.getMenuOption() == 3) {
				this.deleteBook();
			}
			this.drawScreen();
		}
	}
	
	public void updateBook() {
		if(this.bookScreen.updateBook(this.selectedBook)) {
			String title = this.bookScreen.getBookInfo().get(0);
			this.selectedBook.setTitle(title);
			this.selectedBook.update();
		}
		this.drawScreen();
	}
	
	public void deleteBook() {
		this.selectedBook.delete();
	}
	
	private void drawEditPublisher() {
		this.adminScreen.drawAddUpdateOrDelete("Publisher");
		
		if(this.adminScreen.getMenuOption() == 1) {
			this.addNewPublisher();
		} else if(this.adminScreen.getMenuOption() == 4) {
			this.drawScreen();
		} else {
			this.editPublisher();
		}
	}
	
	private void addNewPublisher() {
		if(this.publisherScreen.drawAddOrUpdateAPublisher()) {
			
			Publisher publisher = new Publisher(
					this.publisherScreen.getPubInfo().get(0),
					this.publisherScreen.getPubInfo().get(1),
					this.publisherScreen.getPubInfo().get(2)
			);
			
			publisher.save();
		}
		this.drawScreen();
	}
	
	private void editPublisher() {
		this.publishers = Publisher.getAll();
		this.publisherScreen.drawPickAPublisher(publishers);
		
		if(this.publisherScreen.getMenuOption() > this.publishers.size()) {
			this.drawScreen();
		} else {
			this.selectedPublisher = this.publishers.get(this.publisherScreen.getMenuOption() - 1);
			
			if(this.adminScreen.getMenuOption() == 2) {
				this.updatePublisher();
			} else if(this.adminScreen.getMenuOption() == 3) {
				this.deletePublisher();
			}
			this.drawScreen();
		}	
	}
	
	private void updatePublisher() {
		if(this.publisherScreen.updatePublisher(this.selectedPublisher)) {
			String publisherName = this.publisherScreen.getPubInfo().get(0);
			String publisherAddress = this.publisherScreen.getPubInfo().get(1);
			String publisherPhone = this.publisherScreen.getPubInfo().get(2);
			boolean changed = false;
			
			if(publisherName != null) {
				this.selectedPublisher.setPublisherName(publisherName);
				changed = true;
			}
			
			if(publisherAddress != null) { 
				this.selectedPublisher.setPublisherAddress(publisherAddress);
				changed = true;
			}
			
			if(publisherPhone != null) {
				this.selectedPublisher.setPublisherPhone(publisherPhone);
				changed = true;
			}
			
			if(changed) {
				this.selectedPublisher.update();
			}
		}
		this.drawScreen();
	}
	
	private void deletePublisher() {
		this.selectedPublisher.delete();
	}

	private void drawEditLibraryBranch() {
		this.adminScreen.drawAddUpdateOrDelete("Library Branch");
		
		if(this.adminScreen.getMenuOption() == 1) {
			this.addNewBranch();
		} else if(this.adminScreen.getMenuOption() == 4) {
			this.drawScreen();
		} else {
			this.editBranch();
		}
	}
	
	private void addNewBranch() {
		if(this.libScreen.drawAddOrUpdateABranch()) {
			
			LibraryBranch branch = new LibraryBranch(
					this.libScreen.getInput().get(0),
					this.libScreen.getInput().get(1)
			);
			
			branch.save();
		}
		this.drawScreen();
	}
	
	private void editBranch() {
		this.branches = LibraryBranch.getAll();
		this.libScreen.drawPickTheBranch(this.branches);
		
		if(this.libScreen.getMenuOption() > this.branches.size()) {
			this.drawScreen();
		} else {
			this.selectedBranch = this.branches.get(this.libScreen.getMenuOption() - 1);
			
			if(this.adminScreen.getMenuOption() == 2) {
				this.updateBranch();
			} else if(this.adminScreen.getMenuOption() == 3) {
				this.deleteBranch();
			}
			this.drawScreen();
		}
	}

	private void updateBranch() {
		if(this.libScreen.updateLibrary(this.selectedBranch)) {
			String branchName = this.libScreen.getInput().get(0);
			String branchAddress = this.libScreen.getInput().get(1);
			boolean changed = false;
			
			if(branchName != null) {
				this.selectedBranch.setBranchName(branchName);
				changed = true;
			}
			
			if(branchAddress != null) {
				this.selectedBranch.setBranchAddress(branchAddress);
				changed = true;
			}
			
			if(changed) {
				this.selectedBranch.update();
			}
		}
		this.drawScreen();
	}
	
	private void deleteBranch() {
		this.selectedBranch.delete();
	}
	
	private void drawEditBorrower() {
		this.adminScreen.drawAddUpdateOrDelete("Borrower");
		
		if(this.adminScreen.getMenuOption() == 1) {
			this.addNewBorrower();
		} else if(this.adminScreen.getMenuOption() == 4) {
			this.drawScreen();
		} else {
			this.editBorrower();
		}
	}
	
	private void addNewBorrower() {
		if(this.borrowerScreen.drawAddOrUpdateABorrower()) {
			
			Borrower borrower = new Borrower(
				this.borrowerScreen.getBorrowerInfo().get(0),
				this.borrowerScreen.getBorrowerInfo().get(1),
				this.borrowerScreen.getBorrowerInfo().get(2)
			);
			
			borrower.save();
		}
		this.drawScreen();
	}
	
	private void editBorrower() {
		this.borrowers = Borrower.getAll();
		this.borrowerScreen.drawPickABorrower(this.borrowers);
		
		if(this.borrowerScreen.getMenuOption() > this.borrowers.size()) {
			this.drawScreen();
		} else {
			this.selectedBorrower = this.borrowers.get(this.borrowerScreen.getMenuOption() - 1);
			
			if(this.adminScreen.getMenuOption() == 2) {
				this.updateBorrower();
			} else if(this.adminScreen.getMenuOption() == 3) {
				this.deleteBorrower();
			}
			this.drawScreen();
		}
	}
	
	private void updateBorrower() {
		if(this.borrowerScreen.updateBorrower(this.selectedBorrower)) {
			String borrowerName = this.borrowerScreen.getBorrowerInfo().get(0);
			String borrowerAddress = this.borrowerScreen.getBorrowerInfo().get(1);
			String borrowerPhone = this.borrowerScreen.getBorrowerInfo().get(2);
			boolean changed = false;
			
			if(borrowerName != null) {
				this.selectedBorrower.setName(borrowerName);
				changed = true;
			}
			
			if(borrowerAddress != null) {
				this.selectedBorrower.setAddress(borrowerAddress);
				changed = true;
			}
			
			if(borrowerPhone != null) {
				this.selectedBorrower.setPhone(borrowerPhone);
				changed = true;
			}
			
			if(changed) {
				this.selectedBorrower.update();
			}
		}
		this.drawScreen();
	}
	
	private void deleteBorrower() {
		this.selectedBorrower.delete();
	}
	
	private void drawEditDueDateForLoan() {
		this.borrowers = Borrower.getAll();
		this.borrowerScreen.drawPickABorrower(borrowers);
		
		if(this.borrowerScreen.getMenuOption() <= this.borrowers.size()) {

			this.selectedBorrower = this.borrowers.get(this.borrowerScreen.getMenuOption() - 1);
			this.loans = BookLoan.getAllLoansFromBorrower(this.selectedBorrower.getCardNo());
			this.books = LibraryBook.getBooksLoanedFromBorrower(this.selectedBorrower.getCardNo());
			this.loanScreen.drawPickALoan(this.loans, this.books);

			if(this.loanScreen.getMenuOption() <= loans.size()) {
				this.selectedLoan = this.loans.get(this.loanScreen.getMenuOption() - 1);
				this.editLoan();
			}
		}
		
		this.drawScreen();
	}
	
	private void editLoan() {
		if(this.loanScreen.updateLoanDueDate(this.selectedLoan)) {
			Date dueDate = this.loanScreen.getNewDueDate();
			
			this.selectedLoan.setDueDate(dueDate);
			this.selectedLoan.update();
		}
		this.drawScreen();
	}
}
