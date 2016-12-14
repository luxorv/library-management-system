package com.gcit.library.controller;

import com.gcit.library.view.AdminScreen;
import com.gcit.library.view.AuthorScreen;
import com.gcit.library.view.BookScreen;
import com.gcit.library.view.BorrowerScreen;
import com.gcit.library.view.LibrarianScreen;
import com.gcit.library.view.LoanScreen;
import com.gcit.library.view.MainScreen;
import com.gcit.library.view.PublisherScreen;
import com.gcit.library.view.Screen;

public class ScreenFactory {
	
	public static Screen getScreen(String screenName) {
		screenName = screenName.toLowerCase();
		
		switch(screenName) {
			case "main":
				return new MainScreen();
			case "librarian":
				return new LibrarianScreen();
			case "borrower":
				return new BorrowerScreen();
			case "admin":
				return new AdminScreen();
			case "books":
				return new BookScreen();
			case "author":
				return new AuthorScreen();
			case "publisher":
				return new PublisherScreen();
			case "loan":
				return new LoanScreen();
		}
		
		return null;
	}
}
