package com.gcit.library.view;

import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.LibraryBranch;

public class LibrarianScreen implements Screen {
	
	private int menuOption;
	private ArrayList<String> menuInput;
	
	public LibrarianScreen() {
		this.menuInput = new ArrayList<String>();
	}

	@Override
	public void draw() {
		this.drawLibrarianWelcome();
	}
	
	public void drawLibrarianWelcome() {
		System.out.println("\n1) Enter the Branch you manage");
		System.out.println("2) Quit to previous\n");
		
		System.out.print("Enter your option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public void drawLibrarianMenu() {
		System.out.println("\nWhat would you like to do?\n");
		System.out.println("1) Update the details of the Branch");
		System.out.println("2) Add copies of Book to the Branch");
		System.out.println("3) Quit to previous\n");
		
		System.out.print("Enter your option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public boolean updateLibrary(LibraryBranch branch) {
		this.menuInput = new ArrayList<String>();
		System.out.print("\nYou have chosen to update the Branch with Branch Id: ");
		System.out.println(branch.getBranchId() + " and Branch Name: " + branch.getBranchName());
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");
		
		String input;
		System.out.println("Please enter new branch name or enter N/A for no change: ");
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.menuInput.add(input);
		
		System.out.println("Please enter new branch address or enter N/A for no change: ");
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.menuInput.add(input);
		
		return true;
	}
	
	public void drawPickTheBranch(ArrayList<LibraryBranch> branches) {
		System.out.println();
		for (int i = 0; i < branches.size(); i++) {
			LibraryBranch branch = branches.get(i);
			System.out.println((i + 1) + ") " + branch.getBranchName());
		}
		
		System.out.println((branches.size() + 1) + ") Quit to Previous\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}

	public int getMenuOption() {
		return menuOption;
	}

	public ArrayList<String> getInput() {
		return this.menuInput;
	}

	public boolean drawAddOrUpdateABranch() {
		System.out.println("\nInsert 'quit' on any field to cancel the operation");
		this.menuInput = new ArrayList<String>();
		String input;
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		System.out.print("Branch Name: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		menuInput.add(input);
		
		System.out.print("Branch Address: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		menuInput.add(input);
		
		return true;
	}
}
