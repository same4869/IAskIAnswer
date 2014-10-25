package com.xun.iaskianswer.manager;

import android.content.Context;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xun.iaskianswer.app.IAskIAnswerApp;
import com.xun.iaskianswer.entity.bean.LocationInfo;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class BaiduLocationManager {
	private static BaiduLocationManager instance = null;

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 60 * 1000;
	private final static String COOR_TYPE = "gcj02";
	private LocationInfo locationInfo;
	private Context context;

	private BaiduLocationManager() {
		this.context = IAskIAnswerApp.AppContext;
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

	private void setLcationOption(final TextView tv) {
		locationInfo = LocationInfo.getInstance();
		this.locationClient = new LocationClient(context);
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);
		locationClientOption.setCoorType(COOR_TYPE);
		locationClientOption.setScanSpan(UPDATE_TIME);// 扫描周期：1分钟
		locationClientOption.setIsNeedAddress(true);
		this.locationClient.setLocOption(locationClientOption);
		this.locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					return;
				}
				locationInfo.setLatitude(location.getLatitude());
				locationInfo.setLongitude(location.getLongitude());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					locationInfo.setSpeed(location.getSpeed());
					locationInfo.setSatelliteNumber(location.getSatelliteNumber());
					locationInfo.setAddrStr(location.getAddrStr());
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					locationInfo.setAddrStr(location.getAddrStr());
				}
				tv.setText(locationInfo.getAddrStr());
			}
		});
	}

	// 参数中传个TextView，结果就显示在这个TextView中
	public void startLocationUpdate(TextView tv, Context context) {
		setLcationOption(tv);
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
