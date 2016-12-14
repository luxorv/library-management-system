package com.gcit.library.controller;

import java.util.HashMap;

import com.gcit.library.view.Screen;

public class ScreenManager {

	HashMap<String, Screen> screens;
	private static ScreenManager instance;
	
	private ScreenManager() {
		// TODO fill out screens HashMap.
		this.screens = new HashMap<String, Screen>();
	}
	
	public static ScreenManager getInstance() {
		
		if(ScreenManager.instance == null) {
			ScreenManager.instance = new ScreenManager();
		}
		return ScreenManager.instance;
	}
	
	public Screen getScreen(String screenName) {
		
		if(!screens.containsKey(screenName)) {
			screens.put(screenName, ScreenFactory.getScreen(screenName));
		}
		return screens.get(screenName);
	}
}
