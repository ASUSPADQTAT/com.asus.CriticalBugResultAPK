package com.asus.CriticalBugResultAPK;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "criticalBugManager";

	// Contacts table name
	private static final String TABLE_TESTRUNS = "testrun";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DOMAIN = "domain";
	private static final String KEY_TCID = "tc_id";
	private static final String KEY_TCNAME = "tc_name";
	private static final String KEY_AUTOTYPE = "auto_type";
	private static final String KEY_SCREENSHOTURI = "screenshot_uri";
	private static final String KEY_RESULT = "result";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TESTRUNS_TABLE = "CREATE TABLE " + TABLE_TESTRUNS + "(" 
	+ KEY_ID + " INTEGER PRIMARY KEY,"
	+ KEY_DOMAIN + " TEXT," 
	+ KEY_TCID + " TEXT," 
	+ KEY_TCNAME + " TEXT," 
	+ KEY_AUTOTYPE + " TEXT," 
	+ KEY_SCREENSHOTURI + " TEXT," 
	+ KEY_RESULT+ " TEXT" + ")";
		db.execSQL(CREATE_TESTRUNS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTRUNS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new CriticalBug
	public void addCriticalBug(CriticalBug bug) {
		SQLiteDatabase db = this.getWritableDatabase();

		Log.d("addCriticalBug ","id " + bug.ID + " " +bug.TCID + " "+ bug.TCName);
		
		
		ContentValues values = new ContentValues();

		
		values.put(KEY_DOMAIN, bug.Domain); // DOMAIN
		values.put(KEY_TCID, bug.TCID); // TCID
		values.put(KEY_TCNAME, bug.TCName); // TCNAME
		values.put(KEY_AUTOTYPE, bug.AutoType); // AUTOTYPE
		values.put(KEY_SCREENSHOTURI, bug.ScreenShotURI); // SCREENSHOT URI
		values.put(KEY_RESULT, bug.Result); // RESULT

		// Inserting Row
		db.insert(TABLE_TESTRUNS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single CriticalBug
	public CriticalBug getCriticalBug(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TESTRUNS, new String[] { KEY_ID, KEY_DOMAIN, KEY_TCID, KEY_TCNAME, KEY_AUTOTYPE, KEY_SCREENSHOTURI, KEY_RESULT }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		CriticalBug bug = new CriticalBug(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return CriticalBug
		return bug;

	}

	// Getting All CriticalBug
	public List<CriticalBug> getAllCriticalBug() {
		List<CriticalBug> bugList = new ArrayList<CriticalBug>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TESTRUNS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				CriticalBug bug = new CriticalBug();
				bug.ID = (Integer.parseInt(cursor.getString(0)));
				bug.Domain = cursor.getString(1);
				bug.TCID = cursor.getString(2);
				bug.TCName = cursor.getString(3);
				bug.AutoType = cursor.getString(4);
				bug.ScreenShotURI = cursor.getString(5);
				bug.Result = cursor.getString(6);

				// Adding contact to list
				bugList.add(bug);
			} while (cursor.moveToNext());
		}

		// return contact list
		return bugList;
	}

	// Getting CriticalBug Count
	public int getCriticalBugCount() {
		Cursor cursor = null;
		try {
			String countQuery = "SELECT  * FROM " + TABLE_TESTRUNS;
			SQLiteDatabase db = this.getReadableDatabase();
			cursor = db.rawQuery(countQuery, null);
			cursor.close();
			// return count
			return cursor.getCount();
		} catch (Exception ex) {
			ex.getMessage();
			return -1;
		}

		finally {
			if (cursor != null)
				cursor.close();
		}

	}

	// Updating single CriticalBug
	public int updateCriticalBug(CriticalBug bug) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_DOMAIN, bug.Domain); // DOMAIN
		values.put(KEY_TCID, bug.TCID); // TCID
		values.put(KEY_TCNAME, bug.TCName); // TCNAME
		values.put(KEY_AUTOTYPE, bug.AutoType); // AUTOTYPE
		values.put(KEY_SCREENSHOTURI, bug.ScreenShotURI); // SCREENSHOT URI
		values.put(KEY_RESULT, bug.Result); // RESULT

		// updating row
		return db.update(TABLE_TESTRUNS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(bug.ID) });
	}

	// Deleting single CriticalBug
	public void deleteCriticalBug(CriticalBug bug) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TESTRUNS, KEY_ID + " = ?",
				new String[] { String.valueOf(bug.ID) });
		db.close();
	}

}