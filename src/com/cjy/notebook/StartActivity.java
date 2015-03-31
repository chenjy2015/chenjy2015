package com.cjy.notebook;

import com.cjy.notebook.global.CJYApplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
/**
 * @author chenjiayou
 * @feature 启动类
 * @createTime: 2014.11.3
 * @category: CJY Studio
 */
public class StartActivity extends Activity{

	ImageView iv_welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CJYApplication.activityList.add(this);
		setContentView(R.layout.activity_start);
		iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
		AnimationDrawable oa = (AnimationDrawable) iv_welcome.getBackground();
		oa.start();
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	public Handler handler = new Handler(){
		
		public void dispatchMessage(android.os.Message msg) {
			
			Intent intent = new Intent();
			intent.setClass(StartActivity.this, MainActivity.class);
			startActivity(intent);
		};	
	};
}
