package com.cjy.notebook.service;

import com.cjy.notebook.config.Common;
import com.cjy.notebook.thread.ThreadHelper;
import com.cjy.notebook.thread.ThreadHelper.RunnableCallback;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class IService extends Service{

	private Context mContext;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		int cmb = bundle.getInt("cmb");
		if(cmb == Common.CMB_DELETETABLE){
			ThreadHelper.getInstance().newRunnable(new RunnableCallback() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("result", result);
					intent.putExtras(bundle);
					intent.setAction(Common.DATABASE_DELTETABLE_FROM_NOTES);
					sendBroadcast(intent);
				}
			});
		}
		return super.onStartCommand(intent, flags, startId);
	}

	
}
