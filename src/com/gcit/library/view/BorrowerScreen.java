package com.gcit.library.view;

import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.Borrower;

public class BorrowerScreen implements Screen {

	private int cardNo;
	private int menuOption;
	private ArrayList<String> borrowerInfo;
	
	public BorrowerScreen() {
	}
	
	@Override
	public void draw() {
		drawAuthentication();
	}
	
	public void drawAuthentication() {
		System.out.print("\nEnter your Card Number: ");
		this.cardNo = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public void drawBorrowerMenu() {
		System.out.println("\nWhat would you like to do?\n");
		System.out.println("1) Checkout a book");
		System.out.println("2) Return a book");
		System.out.println("3) Quit to previous\n");
		
		System.out.print("Enter your option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public void drawPickABorrower(ArrayList<Borrower> borrowers) {
		System.out.println();
		for (int i = 0; i < borrowers.size(); i++) {
			Borrower borrower = borrowers.get(i);
			System.out.println((i + 1) + ") " + borrower.getName());
		}
		
		System.out.println((borrowers.size() + 1) + ") Quit to Previous\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public boolean updateBorrower(Borrower borrower) {
		this.borrowerInfo = new ArrayList<String>();
		System.out.print("\nYou have chosen to update the Borrower with Borrower Card No: ");
		System.out.println(borrower.getCardNo() + " and Borrower Name: " + borrower.getName());
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.\n");
		
		String input;
		System.out.println("Please enter new borrower name or enter N/A for no change: ");
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);
		
		System.out.println("Please enter new borrower address or enter N/A for no change: ");
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);
		
		System.out.println("Please enter new borrower phone or enter N/A for no change: ");
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);

		return true;
	}
	
	public boolean drawAddOrUpdateABorrower() {
		System.out.println("\nInsert 'quit' on any field to cancel the operation");
		this.borrowerInfo = new ArrayList<String>();
		String input;
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		System.out.print("Borrower Name: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);
		
		System.out.print("Borrower Address: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);
		
		System.out.print("Borrower Phone: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.borrowerInfo.add(input);
		
		return true;
	}
	
	public void getValidCardNo() {
		System.out.print("Sorry, this card number is invalid, try another one: ");
		this.cardNo = InputRetriever.getInstance().getScanner().nextInt();
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public int getMenuOption() {
		return menuOption;
	}

	public void setMenuOption(int menuOption) {
		this.menuOption = menuOption;
	}
	
	public ArrayList<String> getBorrowerInfo() {
		return this.borrowerInfo;
	}
}
