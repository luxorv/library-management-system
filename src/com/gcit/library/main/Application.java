package com.gcit.library.main;

import com.gcit.library.controller.ScreenManager;

public class Application {
	public static void main(String[] args) {
		ScreenManager.getInstance().getScreen("main").draw();
	}
}
