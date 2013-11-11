package com.cy.riding.ui;


import com.cy.riding.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Tsinghua.wang
 *
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener {
	
	private Fragment mContext ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View menuFrame = inflater.inflate(R.layout.left_menu_frame, container , false); 
		View meterView = menuFrame.findViewById(R.id.btn_meter);
		View aboutView = menuFrame.findViewById(R.id.btn_about);
		View settingsView = menuFrame.findViewById(R.id.btn_settings);

		
		meterView.setOnClickListener(this);
		aboutView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
		
		return menuFrame ;
	}

	@Override
	public void onClick(View v) {
		
		mContext = null ;
		
		switch(v.getId()){
			case R.id.btn_meter:
				mContext = new MeterFragment();
				break ;
			case R.id.btn_about:
				mContext = new AboutFragment();
				break ;
			case R.id.btn_settings:
				mContext = new SettingsFragment();
				break ;
		}
		switchFragment(mContext);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment){
		if(getActivity() == null ){
			return ;
		}
		
		if(getActivity() instanceof RidingActivity){
			
			RidingActivity ridingActivity = (RidingActivity)getActivity();
			ridingActivity.switchContent(fragment);
			
		}
	}
}
