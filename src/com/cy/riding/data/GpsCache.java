package com.cy.riding.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.location.Location;

/**
 * ����GPS����. 
 * @author tsinghua
 *
 */
public class GpsCache {

	//GPS��ʱ��������, �Ỻ��7����֮�ڵ�����, ÿ��5���ӻ��ǰ5����֮�ڵ����ݳ־�
	//�����ݿ�,����gpsColl������5����֮�ڵ�����. ���㵱ǰ�ٶȿ��Դ�gpsColl��ȡ
	//���õ���Ϣ.
	private static final HashMap<Long , Location> gpsColl = new HashMap<Long , Location>();
	
	public static void addGps(Location value){
		long key = System.currentTimeMillis();
		gpsColl.put(key, value);
	}
	
	public static List<Location> getIntervalGps(long start , long end){

		Set<Long> keys = gpsColl.keySet();
		Iterator<Long> iter = keys.iterator();
		List<Location> gpsLst = new ArrayList<Location>();
		while(iter.hasNext()){
			long key = iter.next();
			if(key >= start && key < end ){
				gpsLst.add(gpsColl.get(key));
			}
		}
		return gpsLst;
	}
}
