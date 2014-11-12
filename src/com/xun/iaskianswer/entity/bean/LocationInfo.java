package com.xun.iaskianswer.entity.bean;

/**
 * @author xwang
 *
 * 2014年10月24日
 */
public class LocationInfo {
	private double latitude, longitude;
	private float speed;
	private int satelliteNumber;
	private String addrStr;
	
	private static LocationInfo instance = null;
	
	private LocationInfo(){
	}
	
	public static LocationInfo getInstance(){
		if(instance == null){
			init();
		}
		return instance;
	}
	
	private synchronized static void init(){
		if(instance == null){
			instance = new LocationInfo();
		}
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getSatelliteNumber() {
		return satelliteNumber;
	}

	public void setSatelliteNumber(int satelliteNumber) {
		this.satelliteNumber = satelliteNumber;
	}

	public String getAddrStr() {
		return addrStr;
	}

	public void setAddrStr(String addrStr) {
		this.addrStr = addrStr;
	}

}
