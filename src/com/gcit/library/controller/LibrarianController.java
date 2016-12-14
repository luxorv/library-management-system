package com.gcit.library.controller;

import java.util.ArrayList;

import com.gcit.library.model.BookCopies;
import com.gcit.library.model.LibraryBook;
import com.gcit.library.model.LibraryBranch;
import com.gcit.library.view.BookScreen;
import com.gcit.library.view.LibrarianScreen;

public class LibrarianController implements Controller {
	
	LibrarianScreen libScreen;
	BookScreen bookScreen;
	LibraryBranch selectedBranch;
	LibraryBook selectedBook;
	ArrayList<LibraryBranch> branches;
	ArrayList<LibraryBook> books;
	
	public LibrarianController() {
		this.branches = null;
	}
	
	public void drawScreen() {
		this.libScreen = (LibrarianScreen) ScreenManager.getInstance().getScreen("librarian");
		this.libScreen.draw();
		
		if(this.libScreen.getMenuOption() == 1) {
			this.pickBranch();
			this.drawLibrarianMenu();
		} else if(this.libScreen.getMenuOption() == 2) {
			ScreenManager.getInstance().getScreen("main").draw();
		}
		
	}
	
	public void pickBranch() {
		
		if(this.branches == null) {
			this.branches = LibraryBranch.getAll();
		}
		
		this.libScreen.drawPickTheBranch(this.branches);
		
		if(this.libScreen.getMenuOption() > this.branches.size()) {
			this.drawScreen();
		} else {
			this.selectedBranch = this.branches.get(this.libScreen.getMenuOption() - 1);
		}
	}
	
	public void drawLibrarianMenu() {
		this.libScreen.drawLibrarianMenu();
		
		if(this.libScreen.getMenuOption() == 1) {
			this.updateBranch();
		} else if(this.libScreen.getMenuOption() == 2) {
			this.bookScreen = (BookScreen) ScreenManager.getInstance().getScreen("books");
			this.addCopiesToBook();
		} else if(this.libScreen.getMenuOption() == 3) {
			this.pickBranch();
		}
		
		this.drawLibrarianMenu();
	}
	
	public void addCopiesToBook() {
		this.books = LibraryBook.getAll();
		this.bookScreen.drawBooksToAddCopiesOnBranch(this.books);
		
		if(this.bookScreen.getMenuOption() > this.books.size()) {
			this.drawLibrarianMenu();
		} else {
			this.selectedBook = this.books.get(this.bookScreen.getMenuOption() - 1);
			
			BookCopies copies = BookCopies.get(
					this.selectedBook.getBookId(),
					this.selectedBranch.getBranchId()
			);
			
			this.bookScreen.drawNumberOfCopies(copies.getNoOfCopies());

			int numberOfCopies = this.bookScreen.getNumberOfCopies();
			
			if(numberOfCopies != copies.getNoOfCopies() && numberOfCopies >= 0) {

				copies.setNoOfCopies(copies.getNoOfCopies() + numberOfCopies);
				
				if(copies.wasFound()) {
					copies.update();
					System.out.println("Copies updated to: " + copies.getNoOfCopies());
				} else {
					copies.save();
					System.out.println("Copies are now: " + copies.getNoOfCopies());
				}
			}
			this.drawLibrarianMenu();
		}
	}
	
	public void updateBranch() {
		
		if(this.libScreen.updateLibrary(this.selectedBranch)) {
			String newName = this.libScreen.getInput().get(0);
			String newAddress = this.libScreen.getInput().get(1);
			boolean changed = false;
			
			if(newName != null) {
				this.selectedBranch.setBranchName(newName);
				changed = true;
			}
			
			if(newAddress != null) {
				this.selectedBranch.setBranchAddress(newAddress);
			}
			
			if(changed) {
				this.selectedBranch.update();
			}
		}
		this.drawLibrarianMenu();
	}
}
