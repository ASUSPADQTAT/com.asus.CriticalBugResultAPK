package com.asus.CriticalBugResultAPK;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainService extends IntentService {

	public static Context MainServiceContext;
	public Intent _intent;

	public MainService() {
		super("MainService");

	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		_intent = intent;
		MainServiceContext = getApplicationContext();
		try {
			Log.d("CriticalBug", "com.asus.CriticalBugResultAPK onHandleIntent");
			String Domain = _intent.getStringExtra("Domain");
			String TCID = _intent.getStringExtra("TCID");
			String TCName = _intent.getStringExtra("TCName");
			String AutoType = _intent.getStringExtra("AutoType");
			String ScreenShotURI = _intent.getStringExtra("ScreenShotURI");
			String Result = _intent.getStringExtra("Result");

			AddTestCaseToDB(Domain, TCID, TCName, AutoType, ScreenShotURI, Result);
			SwitchActivtiy();

		} catch (Exception ex) {
			Log.d("CriticalBug", "error");
		}
	}

	private void AddTestCaseToDB(String domain, String tCID, String tCName, String autoType, String screenShotURI, String result) {		
		// save CriticalBug object to database
		Log.d("Insert: ", "Inserting ..");
		DatabaseHandler db = new DatabaseHandler(this);
		db.addCriticalBug(new CriticalBug(domain, tCID, tCName, autoType, screenShotURI, result));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		String action = _intent.getAction();
	}

	private void SwitchActivtiy() {
		try {
			Intent mIntent = new Intent(MainServiceContext, ResultActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MainServiceContext.startActivity(mIntent);
		} catch (Exception ex) {
			Log.d("CriticalBug", ex.getMessage().toString());
		}

	}

}
