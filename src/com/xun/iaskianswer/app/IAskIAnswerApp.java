package com.xun.iaskianswer.app;

import android.app.Application;
import android.content.Context;

public class IAskIAnswerApp extends Application {
	public static Context AppContext;
	private static IAskIAnswerApp instance;

	public static IAskIAnswerApp getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		AppContext = this;
		instance = this;
		super.onCreate();
	}
}
