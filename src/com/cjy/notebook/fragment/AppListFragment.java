package com.cjy.notebook.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cjy.notebook.R;
import com.cjy.notebook.adapter.AppListAdapter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AppListFragment extends Fragment{

	private List<ResolveInfo> apps;
	private List<ActivityInfo> apis;
	private List<ApplicationInfo> appis;
	private AppListAdapter mAdapter;
	private ListView app_lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_application_list, container, false);
		app_lv = (ListView) view.findViewById(R.id.app_lv);
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
		
		return view;
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
        apps = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        mAdapter = new AppListAdapter(getActivity().getApplicationContext(), apps);
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
