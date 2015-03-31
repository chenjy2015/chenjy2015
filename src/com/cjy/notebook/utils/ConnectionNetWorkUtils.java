package com.cjy.notebook.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionNetWorkUtils {

	private Context mContext;
	private static ConnectionNetWorkUtils mInstance;
	
	private ConnectionNetWorkUtils(Context mContext){
		this.mContext = mContext;
	}
	
	public static ConnectionNetWorkUtils getInstance(Context mContext){
		if(mInstance == null){
			mInstance = new ConnectionNetWorkUtils(mContext);
		}
		return mInstance;
	}
	
	public boolean CheckNetWork(){
		if(isNetworkConnected()){
			if(isNetWorkWifi()){
				return true;
			}
			if(isNetWorkMobel()){
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 检测当前网络是否可用
	 * */
	private  boolean isNetworkConnected() {
		if (mContext != null) {
			ConnectivityManager connManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测当前网络是否是WiFi
	 * */
	private boolean isNetWorkWifi() {
		if (mContext != null) {
			ConnectivityManager connManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}

		return false;
	}
	
	/**
	 * 检测当前网络是否是手机网络
	 * */
	private boolean isNetWorkMobel() {
		if (mContext != null) {
			ConnectivityManager connManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}

		return false;
	}
}
