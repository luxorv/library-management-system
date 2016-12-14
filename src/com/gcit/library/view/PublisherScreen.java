package com.gcit.library.view;

import java.util.ArrayList;

import com.gcit.library.controller.InputRetriever;
import com.gcit.library.model.Publisher;

public class PublisherScreen implements Screen {

	private int menuOption;
	private ArrayList<String> pubInfo;
	
	public PublisherScreen() {
		
	}
	
	@Override
	public void draw() {
		
	}
	
	public boolean drawAddOrUpdateAPublisher() {
		System.out.println("\nInsert 'quit' on any field to cancel the operation\n");
		this.pubInfo = new ArrayList<String>();
		String input;
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		System.out.print("Publisher name: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		pubInfo.add(input);
		
		System.out.print("Publisher Address: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		pubInfo.add(input);
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		System.out.print("Publisher Phone: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		pubInfo.add(input);
		
		return true;
	}
	
	public void drawPickAPublisher(ArrayList<Publisher> publishers) {
		System.out.println("\nSelect a publisher to update or delete\n");
		
		for (int i = 0; i < publishers.size(); i++) {
			Publisher publisher = publishers.get(i);
			System.out.println((i + 1) + ") " + publisher.getPublisherName());
		}
		
		System.out.println((publishers.size() + 1) + ") Cancel operation\n");
		System.out.print("Enter your option here: ");
		
		this.menuOption = InputRetriever.getInstance().getScanner().nextInt();
	}
	
	public boolean updatePublisher(Publisher publisher) {
		this.pubInfo = new ArrayList<String>();
		
		System.out.print("\nYou have chosen to update the Book with Id: ");
		System.out.print(publisher.getPublisherName() + " and Address: ");
		System.out.println(publisher.getPublisherAddress() + " and Phone: " + publisher.getPublisherPhone());
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.\n");
		
		String input = null;
		System.out.println("Please enter new name or enter N/A for no change: ");
		
		InputRetriever.getInstance().getScanner().nextLine();
		
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.pubInfo.add(input);
		
		System.out.println("Please enter new address or enter N/A for no change: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.pubInfo.add(input);
		
		System.out.println("Please enter new phone or enter N/A for no change: ");
		input = InputRetriever.getInstance().getScanner().nextLine();
		
		if(input.toLowerCase().equals("quit")) {
			return false;
		} else if(input.toLowerCase().equals("n/a")) {
			input = null;
		}
		
		this.pubInfo.add(input);
		
		return true;
	}
	
	public ArrayList<String> getPubInfo() {
		return this.pubInfo;
	}
	
	public int getMenuOption() {
		return this.menuOption;
	}

}
