package com.xun.iaskianswer.manager;

import android.content.Context;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class BaiduLocationManager {
	private static BaiduLocationManager instance = null;

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private final static String COOR_TYPE = "gcj02";

	private BaiduLocationManager() {
	}

	public static BaiduLocationManager getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new BaiduLocationManager();
		}
	}

	public static synchronized void destoryInstance() {
		if (instance != null) {
			instance.stopLocationUpdate();
			instance = null;
		}
	}

	private void setLcationOption(final TextView tv, Context context) {
		this.locationClient = new LocationClient(context);
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);
		locationClientOption.setCoorType(COOR_TYPE);
		locationClientOption.setScanSpan(UPDATE_TIME);// 扫描周期：1分钟
		this.locationClient.setLocOption(locationClientOption);
		this.locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					return;
				}
				StringBuffer sb = new StringBuffer(256);
				sb.append("Time : ");
				sb.append(location.getTime());
				sb.append("\nError code : ");
				sb.append(location.getLocType());
				sb.append("\nLatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nLontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nRadius : ");
				sb.append(location.getRadius());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					sb.append("\nSpeed : ");
					sb.append(location.getSpeed());
					sb.append("\nSatellite : ");
					sb.append(location.getSatelliteNumber());
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					sb.append("\nAddress : ");
					sb.append(location.getAddrStr());
				}
				tv.setText(sb.toString());
			}
		});
	}

	// 参数中传个TextView，结果就显示在这个TextView中
	public void startLocationUpdate(TextView tv, Context context) {
		setLcationOption(tv, context);
		if (locationClient != null) {
			locationClient.start();
			locationClient.requestLocation();
		}
	}

	public void stopLocationUpdate() {
		if (locationClient != null) {
			locationClient.stop();
		}
	}

}
