package com.cy.riding.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class RidingDBHelper extends SQLiteOpenHelper {

	private final String LOGTAG = "RidingDBHelper" ;
	private boolean DEBUG = false ;
	public RidingDBHelper dbHelper ;
	public final static String DATABASE_NAME = "riding.db";
	public final static int DATABASE_VERSION = 1 ;
	
	public RidingDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);//通过DatabaseHelper定义数据库 
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		if(DEBUG)Log.d(LOGTAG , " [onCreate] db = "+db);
		try {
			if(DEBUG)Log.d(LOGTAG , " [RidingDBHelper] create table . SQL_CREATE_TABLE = "+LocaProviderData.SQL_CREATE_TABLE);
			db.execSQL(LocaProviderData.SQL_CREATE_TABLE);
		} catch (Exception e) {
			if(DEBUG)Log.d(LOGTAG , " [RidingDBHelper] error => "+ e.getMessage());
		} 
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + LocaProviderData.SQL_CREATE_TABLE);
        onCreate(db);
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

