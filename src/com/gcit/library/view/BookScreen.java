package com.gcit.library.view;

import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.LibraryBook;

public class BookScreen implements Screen {

	private int menuOption;
	private int numberOfCopies;
	private ArrayList<String> bookInfo;
	
	public BookScreen() {
	
	}
	
	@Override
	public void draw() {
		
	}
	
	public void drawBooksOnBranch(ArrayList<LibraryBook> books) {
		System.out.println();
		for (int i = 0; i < books.size(); i++) {
			LibraryBook book = books.get(i);
			System.out.println((i + 1) + ") " + book.getTitle());
		}
		
		System.out.println((books.size() + 1) + ") Cancel operation\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public boolean drawAddOrUpdateABook() {
		System.out.println("\nInsert 'quit' on any field to cancel the operation");
		bookInfo = new ArrayList<String>();
		String input;
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		System.out.print("Book title: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		}
		
		bookInfo.add(input);
		
		System.out.print("Publisher Id: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		bookInfo.add(input);
		
		if(input.equals("quit")) {
			return false;
		}
		
		return true;
	}
	
	public boolean updateBook(LibraryBook book) {
		this.bookInfo = new ArrayList<String>();
		
		System.out.print("\nYou have chosen to update the Book with Id: ");
		System.out.println(book.getBookId() + " and Title: " + book.getTitle());
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
		
		String input;
		System.out.println("Please enter new title or enter N/A for no change: ");
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit") || input.toLowerCase().equals("n/a")) {
			return false;
		}
		
		this.bookInfo.add(input);
		return true;
	}
	
	public void drawPickABook(ArrayList<LibraryBook> books) {
		System.out.println("\nSelect a book to update or delete\n");
		
		for (int i = 0; i < books.size(); i++) {
			LibraryBook book = books.get(i);
			System.out.println((i + 1) + ") " + book.getTitle());
		}
		
		System.out.println((books.size() + 1) + ") Cancel operation\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public void drawBooksToAddCopiesOnBranch(ArrayList<LibraryBook> books) {
		System.out.println("\nPick the Book you want to add copies of, to your branch:");
		this.drawBooksOnBranch(books);
	}
	
	public void drawNumberOfCopies(int noOfCopies) {
		System.out.println("\nExisting number of copies: " + noOfCopies + "\n");
		System.out.print("Enter new number of copies(type -1 to cancel operation): ");
		
		this.numberOfCopies = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public ArrayList<String> getBookInfo() {
		return this.bookInfo;
	}
	
	public int getMenuOption() {
		return this.menuOption;
	}

	public int getNumberOfCopies() {
		return numberOfCopies;
	}
}
