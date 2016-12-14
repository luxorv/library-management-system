package com.gcit.library.view;

import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.Author;

public class AuthorScreen implements Screen{

	private int menuOption;
	private String newAuthorName;
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	public void drawAuthorMenu() {
		System.out.println("What do you want to do with the Book's Author(s)?\n");
		System.out.println("1) Select an existing Author");
		System.out.println("2) Add a new Author");
		System.out.println("3) Cancel the operation\n");
		
		System.out.print("Enter your option here: ");
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
		
	}
	
	public void drawPickAnAuthor(ArrayList<Author> authors) {
		for (int i = 0; i < authors.size(); i++) {
			Author author = authors.get(i);
			System.out.println((i + 1) + ") " + author.getAuthorName());
		}
		
		System.out.println((authors.size() + 1) + ") Cancel operation\n");
		System.out.print("Enter your option here: ");
		
		this.setMenuOption(InputRetriever.getInstance().getScanner().nextInt());
	}
	
	public void drawNewAuthor() {
		
		System.out.println("You choose to add a new Author\n");
		System.out.print("Author name: ");
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		this.newAuthorName = InputRetriever.getInstance().getScanner().nextLine();
	}
	
	public String getNewAuthorName() {
		return this.newAuthorName;
	}

	public int getMenuOption() {
		return menuOption;
	}

	public void setMenuOption(int menuOption) {
		this.menuOption = menuOption;
	}

}
