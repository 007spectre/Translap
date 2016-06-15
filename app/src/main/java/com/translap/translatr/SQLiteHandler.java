package com.translap.translatr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_USER = "user";
	private static final String TABLE_USER2 = "user2";
	private static final String TABLE_USER1 = "user1";
	private static final String TABLE_CATEGORY = "eng_cat";
	private static final String TABLE_SUBCATEGORY = "eng_sub";
	private static final String TABLE_TESTLIST = "testlist";
	private static final String tempquestion = "tempquestion";
	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_EMAIL1 = "email";

	// Category Table Columns names
	private static final String C_ID = "id";
	private static final String CATEGORY = "category";

	// sUBCategory Table Columns names

	private static final String S_ID = "id";
	private static final String SCATEGORY = "category";
	private static final String SUBCATEGORY = "subcategory";



	// question Table Columns names


	private static final String Q_ID = "id";
	private static final String TEST = "test";
	private static final String QUESTION = "question";
	private static final String COIN = "coin";


	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE1 = "CREATE TABLE " + TABLE_USER2 + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHOTO + " TEXT"+")";
		db.execSQL(CREATE_LOGIN_TABLE1);


		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT"+")";
		db.execSQL(CREATE_LOGIN_TABLE);



		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
				+ C_ID + " TEXT," + CATEGORY + " TEXT"+")";
		db.execSQL(CREATE_CATEGORY_TABLE);

		String CREATE_SUBCATEGORY_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORY + "("
				+ S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SCATEGORY + " TEXT,"+SUBCATEGORY+"TEXT"+")";
		db.execSQL(CREATE_SUBCATEGORY_TABLE);
		Log.d(TAG, "Database tables created");

		String CREATE_TEST_TABLE = "CREATE TABLE " + TABLE_TESTLIST + "("
				+ Q_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEST + " TEXT,"+QUESTION+"TEXT,"+COIN+"TEXT"+")";
		db.execSQL(CREATE_TEST_TABLE);
		Log.d(TAG, "Database tables created");

		String tempq="create table  qus(id text,ans text)";
		db.execSQL(tempq);
		Log.d(TAG, "Database tables created");


		String category="create table cat(id text,category text)";
		db.execSQL(category);
		Log.d(TAG, "Database tables created");
}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}






	public void inserttempqus(String id,String ans)
	{
		SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("id",id);
		values.put("ans", ans);

		long id1=     sqLiteDatabase.insert("qus",null,values);
		Log.d(TAG, "New question insert: " + id1);

	}



	/**
	 * Storing user details in database
	 * */
	public void addUser(String email) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_EMAIL, email);
	//	values.put(KEY_PHOTO, photo);
		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}
	public void addUser1(String photo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_PHOTO, photo);
		//	values.put(KEY_PHOTO, photo);
		// Inserting Row
		long id = db.insert(TABLE_USER2, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}


	public void addCategory(String id,String category) {
		SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("id",id);
		values.put("category", category);

		long id1=  sqLiteDatabase.insert(TABLE_CATEGORY,null,values);
		Log.d(TAG, "New category category into sqlite: " +id1);
	}


	public void addsubCategory(int id,String category,String subcategory) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(Q_ID, id);
		values.put(CATEGORY, category);
		values.put(SUBCATEGORY, subcategory);
		// Inserting Row
		long id1 = db.insert(TABLE_SUBCATEGORY, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id1);
	}


	public void addTest(HashMap<String, String> queryValues) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(C_ID, queryValues.get("id1"));
		values.put(TEST, queryValues.get("test"));
		values.put(QUESTION, queryValues.get("question"));
		values.put(COIN, queryValues.get("coin"));
		// Inserting Row
		long id1 = db.insert(TABLE_TESTLIST, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New question inserted into sqlite: " + id1);
	}





	public ArrayList fetchData(){

		ArrayList<String>stringArrayList=new ArrayList<String>();
		String fetchdata="select * from cat";
		SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
		Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
		if(cursor.moveToFirst()){
			do
			{
				stringArrayList.add(cursor.getString(0));
				stringArrayList.add(cursor.getString(1));

			} while (cursor.moveToNext());
		}
		return stringArrayList;




	}


	public String  getUserDetails1() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  photo FROM " + TABLE_USER2;
		String bankbalresult="";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				bankbalresult = cursor.getString(0);
			} while (cursor.moveToNext());
		}

		return bankbalresult;

	}



	/**
	 * Getting user data from database
	 * */
	public String  getUserDetails() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  email FROM " + TABLE_USER;
		String bankbalresult="";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				bankbalresult = cursor.getString(0);
			} while (cursor.moveToNext());
		}

		return bankbalresult;

	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
