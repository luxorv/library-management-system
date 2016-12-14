package com.gcit.library.controller;

import java.util.Scanner;

public class InputRetriever {
	private static InputRetriever instance;
	private Scanner scanner;
	
	private InputRetriever() {
		this.scanner = new Scanner(System.in);
	}
	
	public static InputRetriever getInstance() {
		if(InputRetriever.instance == null) {
			InputRetriever.instance = new InputRetriever();
		}
		return InputRetriever.instance;
	}

	public Scanner getScanner() {
		return this.scanner;
	}
}