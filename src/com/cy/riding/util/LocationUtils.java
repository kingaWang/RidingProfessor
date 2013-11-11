package com.cy.riding.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

public class LocationUtils {

	
	public boolean isGpsEnabled(Context context){
		boolean isGpsEnabled = false ;
		
		 LocationManager locationManager =
		            (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	    final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    if (!gpsEnabled) {
	        // Build an alert dialog here that requests that the user enable
	        // the location services, then when the user clicks the "OK" button,
	        // call enableLocationSettings()
	    	isGpsEnabled = false ;
	    }else{
	    	isGpsEnabled = true ;
	    }
	   return isGpsEnabled; 
	}
	
	//进入开启"位置信息"
	public void enableLocationSettings(Context mContext) {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    mContext.startActivity(settingsIntent);
	}
}
