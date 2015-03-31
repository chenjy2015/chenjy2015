package com.cjy.notebook.media;

import android.hardware.Camera;

public class CameraManager {

	private static CameraManager mInstance;
	private CameraManager(){
		
	}
	
	public static CameraManager getInstance(){
		if(mInstance == null){
			mInstance = new CameraManager();
		}
		return mInstance;
	}
	
}
