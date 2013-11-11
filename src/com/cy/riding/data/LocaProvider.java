package com.cy.riding.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class LocaProvider extends ContentProvider {

	//ContentProvider 详解. http://blog.csdn.net/jiahui524/article/details/7016430
	
	private static UriMatcher sUriMatcher ;
	private static final int COLLECTION_INDICATOR = 1;
	private static final int SINGLE_INDICATOR = 2;
	private RidingDBHelper dbHelper ;
	private String TAG = "LocaProvider";
	private boolean ISLOG = true ;
	
	static{
		
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(LocaProviderData.AUTHORITY, "loca", COLLECTION_INDICATOR);
		sUriMatcher.addURI(LocaProviderData.AUTHORITY, "loca/#", SINGLE_INDICATOR);
		
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		 switch(sUriMatcher.match(uri)) {
	        case COLLECTION_INDICATOR:
	            return LocaProviderData.CONTENT_TYPE;
	 
	        case SINGLE_INDICATOR:
	            return LocaProviderData.CONTENT_ITEM_TYPE;
	        default:
	            throw new IllegalArgumentException("Unknow URI: " + uri);
	    }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
		long rowID = db.insert(LocaProviderData.TABLE_NAME, null, values);
	    if(rowID > 0) {
	        Uri retUri = ContentUris.withAppendedId(LocaProviderData.CONTENT_URI, rowID);
	        return retUri;
	    }
		return null;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new RidingDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,  
            String[] selectionArgs, String sortOrder) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(ISLOG)Log.d(TAG , "query , selectionArgs = "+selectionArgs );
		
        switch (sUriMatcher.match(uri)) {  
	        case COLLECTION_INDICATOR:  
	            // 查询所有的数据  
	            return db.query(LocaProviderData.TABLE_NAME, projection, selection, selectionArgs,  
	                    null, null, sortOrder);  
	  
	        case SINGLE_INDICATOR:  
	            // 查询某个ID的数据  
	            // 通过ContentUris这个工具类解释出ID  
	        	// 通过ContentUri工具类得到ID  
	         	long id = ContentUris.parseId(uri);  
	            String where = LocaProviderData._ID +" =" + id;  
	            if (selection != null && !"".equals(selection)) {  
	                where = selection + " and " + where;  
	            }  
	            
	            return db.query(LocaProviderData.TABLE_NAME, projection, where, selectionArgs, null,  
	                    null, sortOrder); 
        default:  
  
            throw new IllegalArgumentException("unknow uri" + uri.toString());  
        }  
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	    int count = -1;
	    switch(sUriMatcher.match(uri)) {
	    case COLLECTION_INDICATOR:
	        count = db.update(LocaProviderData.TABLE_NAME, values, null, null);
	        if(ISLOG)Log.d(TAG , " update(COLLECTION_INDICATOR) ,  count = "+count);
	        break;
	 
	    case SINGLE_INDICATOR:
	        String rowID = uri.getPathSegments().get(1);
	        count = db.update(LocaProviderData.TABLE_NAME, values, LocaProviderData._ID + "=" + rowID, null);
	        if(ISLOG)Log.d(TAG , " update(SINGLE_INDICATOR) , rowID = "+rowID +" , count = "+count);
	        break;
	 
	    default:
	        throw new IllegalArgumentException("Unknow URI : " + uri);
	 
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return count;
	}
	
	

}
