package com.cjy.notebook.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.config.Common;

public class VideoActivity extends Activity implements OnClickListener{

	
	private Button mStartBtn,leftBtn;
	private TextView mTimeTv;
	private ProgressBar mProgress;
	private View titleView;
	private boolean isStart;//标示 视频是否正在录制
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_video);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		
		mStartBtn = (Button) findViewById(R.id.start);
		titleView = findViewById(R.id.title_layout);
		leftBtn = (Button) titleView.findViewById(R.id.left_bt);
		mTimeTv = (TextView) findViewById(R.id.time);
		mProgress = (ProgressBar) findViewById(R.id.progress);
		mStartBtn.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
			Intent intent = new Intent();
			if(!isStart){
				intent.setAction(Common.VIDEO_START_RECORDING);
				mStartBtn.setText("stop");
				isStart = true;
			}else{
				intent.setAction(Common.VIDEO_STOP_RECORDING);
				mStartBtn.setText("start");
				isStart = false;
			}
			sendBroadcast(intent);
			break;
			
		case R.id.left_bt:
			finish();
			break;
		}
		
	}
	
	
	
}
