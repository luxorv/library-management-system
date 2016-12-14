package com.gcit.library.view;

import com.gcit.library.controller.AdminController;
import com.gcit.library.controller.BorrowerController;
import com.gcit.library.controller.Controller;
import com.gcit.library.controller.InputRetriever;
import com.gcit.library.controller.LibrarianController;

public class MainScreen implements Screen {

	int menuOption;
	
	public MainScreen() {

	}
	
	@Override
	public void draw() {
		
		System.out.print("\n\nWelcome to the GCIT Library Management Tool. ");
		System.out.println("Which category of a user are you?\n");
		System.out.println("1) Admin");
		System.out.println("2) Librarian");
		System.out.println("3) Borrower");
		System.out.println("4) Exit\n");
		
		System.out.print("Enter the option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
		
		Controller controller = null;
		
		if(this.menuOption == 1) {
			 controller = new AdminController();
		} else if(this.menuOption == 2) {
			controller = new LibrarianController();
		} else if(this.menuOption == 3) {
			controller = new BorrowerController();
		} else if(this.menuOption >= 4){
			System.exit(0);
		}
		
		controller.drawScreen();
	}
}
