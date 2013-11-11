package com.cy.riding.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class LocaProviderData implements BaseColumns {

	/**
     * 
     *数据库中表相关的元数据
     */
	public final static String AUTHORITY = "com.cy.riding.loca";
    public static final String TABLE_NAME = "loca";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/loca");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.riding.loca";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.riding.loca";
 
  //================table(loca)==================
  	public static final String _ID = "l_id";
  	public static final String LATITUDE = "l_latitude";
  	public static final String LONGITUDE = "l_longitude";
  	public static final String TAG = "l_tag";
  	public static final String TIME = "l_time";
 
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                + _ID + " INTEGER PRIMARY KEY,"
                                + LATITUDE + " double ,"
                                + LONGITUDE + " double ,"
                                + TIME + " long ,"
                                + TAG + " long "
                                + ");" ;
}
