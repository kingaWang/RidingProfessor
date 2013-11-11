package com.cy.riding.ui;

import java.util.ArrayList;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cy.riding.R;

public class MeterFragment extends Fragment {
	
	private LocationManager locationm ;
	private double latitude ;//纬度
	private double longitude ;//经度
	private final double EARTH_RADIUS = 6378137.0;
	
	private final static String TAG = "MeterFragment" ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View meterView = inflater.inflate(R.layout.meter_frame, container , false); 
		
		return meterView ;
	}

	public void initView(View meterView){
		
		if(getActivity() == null){
			Log.w(TAG , "");
			return ;
		}
		locationm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationm.getBestProvider(criteria, true);
		Location location = locationm.getLastKnownLocation(provider);
		gpsLoc(location);
		
		LocationListener gpsLocation = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				gpsLoc(location);
			}
		};
		locationm.requestLocationUpdates(provider, 1000, 0, gpsLocation);//每一秒更新位置信息, 不考虑距离变化
	}
	
	
	public void gpsLoc(Location location ){
		
		if(location != null){
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
		}else{
			latitude = 0 ;
			longitude = 0 ;
		}
	}
	
	
	
	 
	/**
	 * 
	 * 获取两个经纬度之间的距离
	 * 
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
	private double gpsToM(double lat_a, double lng_a, double lat_b, double lng_b) {

	       double radLat1 = (lat_a * Math.PI / 180.0);

	       double radLat2 = (lat_b * Math.PI / 180.0);

	       double a = radLat1 - radLat2;

	       double b = (lng_a - lng_b) * Math.PI / 180.0;

	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

	              + Math.cos(radLat1) * Math.cos(radLat2)

	              * Math.pow(Math.sin(b / 2), 2)));

	       s = s * EARTH_RADIUS;
	      
	       s = Math.round(s * 10000) / 10000;

	       return s;

	 }
	
	
	public void getCellInofs(){
		
	}
	 

	/** 
	 * 两点经纬度，计算方位角
	 * 计算方位角pab 
	 */
	private double gpsToAngle(double lat_a, double lng_a, double lat_b, double lng_b) {
	  double d = 0;
	  lat_a=lat_a*Math.PI/180;
	  lng_a=lng_a*Math.PI/180;
	  lat_b=lat_b*Math.PI/180;
	  lng_b=lng_b*Math.PI/180;
	    
	  d=Math.sin(lat_a)*Math.sin(lat_b)+Math.cos(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);
	  d=Math.sqrt(1-d*d);
	  d=Math.cos(lat_b)*Math.sin(lng_b-lng_a)/d;
	  d=Math.asin(d)*180/Math.PI;
	      
	  d = Math.round(d*10000);
	  
	  return d;
	}
	
}
