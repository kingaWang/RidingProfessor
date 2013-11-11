package com.cy.riding.ui;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cy.riding.R;
import com.cy.riding.data.GpsCache;

public class MeterActivity extends Activity implements OnClickListener  {

	private final String TAG = "MeterActivity" ;
	private final boolean ISLOG= true ;
	LocationManager locationm;
	private double lat ;
	private double lng ;
	private final double EARTH_RADIUS = 6378137.0; 
	private final static int MSG_REFRESH_SPEED = 1 ;
	private final static int MSG_START = 2 ;
	private final static int MSG_RESET = 3 ;
	
	private final static int STATUS_RIDING = 10;
	private final static int STATUS_PAUSED = 11;
	private final static int STATUS_DIAL = 12;
	private int mStatus = STATUS_DIAL ;
	
	private float mCurrSpeed ;
	private float mDistince ;
	private Date mStartDate ;
	private GpsListener mGpsListener ;
	
	private TextView mCurrSpeedTv ;
	private TextView mDistinceTv ;
	private TextView mStartTimeTv ;
	
	private Button mStartBtn ;
	private Button mResetBtn ;
	private Handler mCalcHandler ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meter_layout);
		
		initViews();
		
		locationm = (LocationManager) getSystemService(LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setCostAllowed(true);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    String provider = locationm.getBestProvider(criteria, true);
	    Location location = locationm.getLastKnownLocation(provider);
	    GpsCache.addGps(location);
	    
	    mCalcHandler = new CalcHandler();
	    mGpsListener = new GpsListener();
	    locationm.requestLocationUpdates(provider, 1000, 0, mGpsListener);
	}
	
	private void initViews(){
		
		mCurrSpeedTv = (TextView)findViewById(R.id.curspeed_tv);
		mDistinceTv = (TextView)findViewById(R.id.distance_tv);
		mStartTimeTv = (TextView)findViewById(R.id.starttime_tv);
		
		mStartBtn = (Button)findViewById(R.id.start_btn);
		mStartBtn.setOnClickListener(this);
		mResetBtn = (Button)findViewById(R.id.reset_btn);
		mResetBtn.setOnClickListener(this);
	}
	
	class GpsListener implements LocationListener {

	       //监听位置变化，实时获取位置信息
        @Override
        public void onStatusChanged(String provider, int status,
               Bundle extras) {
	           }

	           @Override
	           public void onProviderEnabled(String provider) {
	           }

	           @Override
	           public void onProviderDisabled(String provider) {
	             
        }

        @Override
        public void onLocationChanged(Location location) {
     	   	//位置发生改变时
        	if(mStatus == STATUS_RIDING){
        		GpsCache.addGps(location);
        	}
        }
    };
    
	class CalcHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
				case MSG_REFRESH_SPEED:
					Meter meter = calcMeter();
					refreshUI(meter);
					break;
				case MSG_START:
					
					mStartDate = new Date(System.currentTimeMillis());
					mStatus = STATUS_RIDING ;
					
					break;
				case MSG_RESET:
					mStatus = STATUS_DIAL ;
					mStartDate = new Date(System.currentTimeMillis());
					mCurrSpeed = 0 ;
					mDistince = 0 ;
					break;
			}
		}
	};
	
	
	public void refreshUI(Meter meter){
		if(meter.currSpeed == 0){
			if(ISLOG)Log.w(TAG , "refreshUI:: speed is zero !!! ");
			return ;
		}
		mCurrSpeedTv.setText(meter.currSpeed +"km/h"); 
		mDistinceTv.setText(meter.distince+"km"); 
	}
	
	public Meter calcMeter(){
		
		Meter meter = new Meter();
		 
		meter.distince = mDistince ;
		long curTime = System.currentTimeMillis();
		long curOnSecondBeforeTime = curTime - 1000 ;
		List<Location> locas = GpsCache.getIntervalGps(curOnSecondBeforeTime, curTime);
		
		if(locas.size() >= 2 ){
			for(int i=0 ; i < locas.size()-1 ; i++){
				Location firLoca = locas.get(i);
				Location secLoca = locas.get(i+1);
				meter.distince += gpsTom(firLoca , secLoca);
			}
		}
		
		meter.currSpeed = meter.distince * 3600 / 1000 ;
		
		return meter ;
	}
	
	
	
	
	// 获得自己位置
	private void getGPSLocation(Location location) {
	
	   if (location != null) {
	       lat = location.getLatitude();
	       lng = location.getLongitude();
	   } else {
		   lat = 0;
		   lng = 0;
	   }
	}

	// 计算两点距离
    private double gpsTom(Location firLoca , Location secLoca) {
    	double lat_a = firLoca.getLatitude();
    	double lng_a = firLoca.getLongitude();
    	double lat_b = secLoca.getLatitude();
    	double lng_b = secLoca.getLongitude();
    	
    	double radLat1 = (lat_a * Math.PI / 180.0);
    	double radLat2 = (lat_b * Math.PI / 180.0);
    	double a = radLat1 - radLat2;
    	double b = (lng_a - lng_b) * Math.PI / 180.0;
    	double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
              + Math.cos(radLat1) * Math.cos(radLat2)
              * Math.pow(Math.sin(b / 2), 2)));
    	s = s * EARTH_RADIUS;
    	s = Math.round(s * 10000) / 10000;
    	return Math.abs(s);

    } 
    
    //Meter
    class Meter {

    	public Date startDate ;	//开始时间
    	public float currSpeed;  //当前速度
    	public float averageSpeed;//平均速度
    	public float distince ;	//距离
    	
    }


	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.start_btn:
				
					mCalcHandler.obtainMessage(MSG_START).sendToTarget();
					Message msg = mCalcHandler.obtainMessage(MSG_REFRESH_SPEED);
					
					mCalcHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							mCalcHandler.obtainMessage(MSG_REFRESH_SPEED).sendToTarget();
						}
					}, 1000);
					break;
			case R.id.reset_btn:
				
					break;
		}
		
	}

}
