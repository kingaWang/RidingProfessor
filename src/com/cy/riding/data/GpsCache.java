package com.cy.riding.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.location.Location;

/**
 * 缓存GPS数据. 
 * @author tsinghua
 *
 */
public class GpsCache {

	//GPS临时缓存数据, 会缓存7分钟之内的数据, 每过5分钟会把前5分钟之内的数据持久
	//到数据库,并从gpsColl清理着5分钟之内的数据. 计算当前速度可以从gpsColl获取
	//有用的信息.
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
