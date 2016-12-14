package com.gcit.library.view;

import com.gcit.library.controller.InputRetriever;

public class AdminScreen implements Screen {
	
	private int menuOption;
	
	public AdminScreen() {
	}
	
	@Override
	public void draw() {
		this.drawAdmingMenu();
	}
	
	public void drawAdmingMenu() {
		System.out.println("\nWhat would you like to do?\n");
		System.out.println("1) Add/Update/Delete Book and Author");
		System.out.println("2) Add/Update/Delete Publishers");
		System.out.println("3) Add/Update/Delete Library Branches");
		System.out.println("4) Add/Update/Delete Borrowers");
		System.out.println("5) Over-ride Due Date for a Book Loan");
		System.out.println("6) Quit to previous\n");
		
		System.out.print("Enter your option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public void drawAddUpdateOrDelete(String model) {
		System.out.println("\nYou chose " + model + "\n");
		System.out.println("1) Add a new " + model);
		System.out.println("2) Update an existing " + model);
		System.out.println("3) Delete an existing " + model);
		System.out.println("4) Quit to previous\n");
		
		System.out.print("Enter option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public int getMenuOption() {
		return this.menuOption;
	}

	public void drawPublisherNotValid() {
		System.out.println("Sorry, this publisher is not valid...");
		System.out.println("Try with a valid Publisher or try adding that Publisher first");
	}
}
