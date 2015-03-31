package com.cjy.notebook;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.cjy.notebook.adapter.ApplicationListAdapter;
import com.cjy.notebook.global.CJYApplication;

public class ApplicationListActivity extends Activity {

	private List<ResolveInfo> apps;
	private List<ActivityInfo> apis;
	private List<ApplicationInfo> appis;
	private ApplicationListAdapter mAdapter;
	private ListView app_lv;
	private Button leftBtn,rightBtn;
	private TextView titleText;
	private View titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		CJYApplication.activityList.add(this);
		setContentView(R.layout.activity_application_list);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		titleView = findViewById(R.id.title);
		titleView = findViewById(R.id.title_layout);
		titleText = (TextView) titleView.findViewById(R.id.textTitle);
		titleText.setText("Application List!");
		leftBtn = (Button) titleView.findViewById(R.id.left_bt);
		leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rightBtn = (Button) titleView.findViewById(R.id.right_bt);
		rightBtn.setVisibility(View.GONE);
		
		app_lv = (ListView) findViewById(R.id.app_lv);
		app_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Intent intentApp = new Intent();
				intentApp.addCategory(Intent.CATEGORY_DEFAULT);
				intentApp.setPackage(appis.get(position).packageName);
				startActivity(intentApp);
				
			}
		});
		initApps();
	}
	
	/**
	 * 获取当前系统所安装的所有应用信息
	 * 1.获取ResolveInfo对象
	 * 2.获取ActivityInfo对象
	 * 3.获取ApplicationInfo对象 通过此对象启动应用
	 * */
	//获取ResolveInfo对象
	public void initApps(){
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = getPackageManager().queryIntentActivities(intent, 0);
        mAdapter = new ApplicationListAdapter(getApplicationContext(), apps);
        app_lv.setAdapter(mAdapter);
        initApis();
	};
	
	//获取ActivityInfo对象
	public void initApis(){
		apis = new ArrayList<ActivityInfo>();
		for(ResolveInfo app : apps){
			apis.add(app.activityInfo);
		}
		initAppis();
	}
	
	//获取ApplicationInfo对象
	public void initAppis(){
		appis = new ArrayList<ApplicationInfo>();
		for(ActivityInfo ai : apis){
			appis.add(ai.applicationInfo);
		}
	}
}
