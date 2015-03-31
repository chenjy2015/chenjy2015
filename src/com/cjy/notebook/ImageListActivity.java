package com.cjy.notebook;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjy.notebook.fragment.GridViewFragment;
import com.cjy.notebook.fragment.ListViewFragment;

public class ImageListActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private TextView type;
	private MyPagerAdapter adapter;
	private ListViewFragment lvf;
	private GridViewFragment gvf;
	private List<Fragment> data;
	private List<String> titles;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagelist);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		type = (TextView) findViewById(R.id.type);
		
		
		
//		mFragManager = getSupportFragmentManager();
		lvf = new ListViewFragment();
		gvf = new GridViewFragment();
		data = new ArrayList<Fragment>();
		titles = new ArrayList<String>();
		data.add(lvf);
		data.add(gvf);
		//titles.add("ImageList");
		titles.add("ImageGrid");
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
	}
	
	class MyPagerAdapter extends FragmentStatePagerAdapter{

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return (data != null && data.size()>0) ? data.get(arg0) : null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null?0:data.size();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//super.destroyItem(container, position, object);
		}
		

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles != null && titles.size() > position ? titles.get(position): "" ;
		}
	};
	
}
