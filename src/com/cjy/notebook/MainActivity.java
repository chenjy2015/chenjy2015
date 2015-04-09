package com.cjy.notebook;

/**
 * @author chenjiayou
 * @feature NotesListActivity 主界面
 * @createTime: 2014.11.3
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjy.notebook.adapter.DrawerLayoutAdapter;
import com.cjy.notebook.adapter.NotesAdapter;
import com.cjy.notebook.code.CommonCode;
import com.cjy.notebook.config.Common;
import com.cjy.notebook.database.DBOperate;
import com.cjy.notebook.fragment.AppListFragment;
import com.cjy.notebook.fragment.NoteListFragment;
import com.cjy.notebook.fragment.WebFragment;
import com.cjy.notebook.global.CJYApplication;
import com.cjy.notebook.media.MusicActivity;
import com.cjy.notebook.media.VideoActivity;
import com.cjy.notebook.object.Notes;
import com.cjy.notebook.utils.DialogUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
public class MainActivity extends FragmentActivity implements OnClickListener,
		DrawerListener {

	private View titleView;
	private TextView titleText;
	private Button leftBtn, rightBtn;
	private NotesAdapter adapter;
	private DrawerLayoutAdapter drawAdapterLeft, drawAdapterRight;
	private DialogUtils dialog;
	private List<Notes> data;
	private DrawerLayout mDrawerLayout;
	private LinearLayout main_layout;// 抽屉中间布局
	private RelativeLayout left_layout;// 抽屉左侧布局
	private RelativeLayout right_layout;// 抽屉右侧布局
	private ListView left_list, right_list;// 抽屉列表
	private List<String> mLeftItems, mRightItems;// 左右抽屉内容
	private AppListFragment appFragment;
	private WebFragment webFragment;
	private NoteListFragment noteFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private int currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		CJYApplication.activityList.add(this);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		main_layout = (LinearLayout) findViewById(R.id.main_layout);
		left_layout = (RelativeLayout) findViewById(R.id.left_layout);
		right_layout = (RelativeLayout) findViewById(R.id.right_layout);
		titleView = findViewById(R.id.title_layout);
		titleText = (TextView) titleView.findViewById(R.id.textTitle);
		titleText.setText("Welcome To CJY Notebook!");
		leftBtn = (Button) titleView.findViewById(R.id.left_bt);
		rightBtn = (Button) titleView.findViewById(R.id.right_bt);
		rightBtn.setText("设置");
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);

		// 抽屉控件
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerListener(this);
		left_list = (ListView) findViewById(R.id.left_lv);
		right_list = (ListView) findViewById(R.id.right_lv);
		left_list.setOnItemClickListener(new ListOnItemClick(left_list));
		right_list.setOnItemClickListener(new ListOnItemClick(right_list));

		initFragment();
		initDrawerLayout();
		setSelectorItem(0);
		
	}

	/**
	 * 初始化Fragment内容
	 * */
	public void initFragment() {
		fragmentManager = getSupportFragmentManager();
		appFragment = new AppListFragment();
		webFragment = new WebFragment();
		noteFragment = new NoteListFragment();
	}

	/**
	 * 初始化抽屉内容
	 * */
	public void initDrawerLayout() {
		mLeftItems = new ArrayList<String>();
		mRightItems = new ArrayList<String>();
		String[] leftitems = getApplicationContext().getResources()
				.getStringArray(R.array.application_list);
		String[] rightitems = getApplicationContext().getResources()
				.getStringArray(R.array.setting_list);
		for (String s1 : leftitems) {
			mLeftItems.add(s1);
		}
		for (String s2 : rightitems) {
			mRightItems.add(s2);
		}
		for (int i = 2; i < 9; i++) {
			mLeftItems.add("菜单列表--" + (i + 1));
			mRightItems.add("菜单列表--" + (i + 1));
		}
		drawAdapterLeft = new DrawerLayoutAdapter(getApplicationContext(),
				mLeftItems);
		left_list.setAdapter(drawAdapterLeft);
		drawAdapterRight = new DrawerLayoutAdapter(getApplicationContext(),
				mRightItems);
		right_list.setAdapter(drawAdapterRight);
	}

	public void show(String prompt) {
		if (dialog != null) {
			dialog = new DialogUtils(prompt);
			dialog.show(getFragmentManager(), "");
		}
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void setSelectorItem(int selector) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (selector) {
		case 0:
			titleText.setText("Notes List!");
			rightBtn.setVisibility(View.VISIBLE);
			rightBtn.setText("设置");
			leftBtn.setVisibility(View.VISIBLE);
			transaction.replace(R.id.main_layout, noteFragment,"noteFragment");
			currentFragment = 0;
			break;
		case 1:
			titleText.setText("Application List!");
			rightBtn.setVisibility(View.GONE);
			transaction.replace(R.id.main_layout, appFragment, "appFragment");
			currentFragment = 1;
			break;
		case 2:
			rightBtn.setVisibility(View.VISIBLE);
			titleText.setText("WebViewActivity");
			rightBtn.setText("LoadWEB");
			transaction.replace(R.id.main_layout, webFragment, "webFragment");
			currentFragment = 2;
			break;
		}
		//transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		transaction.commit();
	}

	public void deleteAll() {
		for (int i = 0; i < data.size(); i++) {
			DBOperate.deleteNote(data.get(i));
		}
	}

	public Handler handler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			data.removeAll(data);
			adapter.notifyDataSetChanged();
			// count = 0;
			dismiss();
		};
	};

	/**
	 * 自定义OnItemClickListener监听事件 为防止多个ListView冲突 在构造函数中初始化调用的ListView
	 * */
	class ListOnItemClick implements OnItemClickListener {

		ListView lv;

		public ListOnItemClick(ListView lv) {
			this.lv = lv;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			switch (lv.getId()) {
			case R.id.left_lv:
				switch (position) {
				case 0:
					setSelectorItem(0);
					break;
				case 1:
					setSelectorItem(1);//在其他非笔记列表界面 屏蔽后台清除和删除列表功能 因为调用到getActivity会报错
					left_list.setItemChecked(2, false);
					left_list.setItemChecked(3, false);
					break;
				case 2:
					noteFragment.handler
							.sendEmptyMessage(Common.DELETE_ON_BACKGROUND);
					break;
				case 3:
					noteFragment.handler.sendEmptyMessage(Common.DELETE);
					break;
				case 4:
					Intent webIntent = new Intent();
					webIntent.setClass(getApplicationContext(), JavaScriptWithWebActivity.class);
					startActivity(webIntent);
					break;
				default:
					setSelectorItem(2);
					left_list.setItemChecked(2, false);
					left_list.setItemChecked(3, false);
					break;
				}
				mDrawerLayout.closeDrawer(left_layout);
				break;
			case R.id.right_lv:
				switch (position) {
				case 1:
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), ImageListActivity.class );
					startActivity(intent);
					break;
				case 2:
					// show("更新数据中......");
					noteFragment.handler.sendEmptyMessage(Common.UPDATE);
					break;
				case 3:
					Intent gsonIntent = new Intent();
					gsonIntent.setClass(getApplicationContext(), GsonActivity.class);
					startActivity(gsonIntent);
					break;
				case 4:
					Intent videoIntent = new Intent();
					videoIntent.setClass(getApplicationContext(), VideoActivity.class);
					startActivity(videoIntent);
					break;
				case 5:
					Intent musicIntent = new Intent();
					musicIntent.setClass(getApplicationContext(), MusicActivity.class);
					startActivity(musicIntent);
					break;
				}
				mDrawerLayout.closeDrawer(right_layout);
				break;
			}
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.left_bt:
			if (mDrawerLayout.isDrawerOpen(left_layout)) {
				mDrawerLayout.closeDrawers();
			} else {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
			if (mDrawerLayout.isDrawerOpen(right_layout)) {
				mDrawerLayout.closeDrawer(right_layout);
			}
			break;

		case R.id.right_bt:
			switch (currentFragment) {
			case 0:
				if (mDrawerLayout.isDrawerOpen(right_layout)) {
					mDrawerLayout.closeDrawers();
				} else {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
				}
				if (mDrawerLayout.isDrawerOpen(left_layout)) {
					mDrawerLayout.closeDrawer(left_layout);
				}
				break;
			case 2:
				webFragment.mHandler.sendEmptyMessage(Common.REFRESH);
				break;

			default:
				break;
			}
			break;
		}

	}

	@Override
	public void onDrawerStateChanged(int newState) {
		// 当滑动抽屉停止后 限制另一边的抽屉不可拖动
//		if (newState == DrawerLayout.STATE_IDLE) {
//			if (left_layout.isActivated()) {
//				right_layout.setEnabled(false);
//			} else {
//				left_layout.setEnabled(false);
//			}
//		}
	}

	/**
	 * 当抽屉在滑动时改变主界面的left或者right坐标 另：做个互斥功能 开启一边抽屉 另一边抽屉要关闭
	 * */
	@Override
	public void onDrawerSlide(View view, float arg1) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.left_layout:
			main_layout.layout(view.getRight(), 0, main_layout.getRight(),
					main_layout.getBottom());
			if(mDrawerLayout.isDrawerOpen(right_layout)){
				mDrawerLayout.closeDrawer(right_layout);
			}
			break;
		case R.id.right_layout:
			main_layout.layout(main_layout.getLeft(), 0, view.getLeft(),
					main_layout.getBottom());
			if(mDrawerLayout.isDrawerOpen(left_layout)){
				mDrawerLayout.closeDrawer(left_layout);
			}
			break;
		}
	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub

		Toast.makeText(getApplicationContext(), "抽屉已打开", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "抽屉已关闭", Toast.LENGTH_SHORT)
				.show();
	}

}
