package com.cjy.notebook.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.cjy.notebook.R;
import com.cjy.notebook.adapter.NotesAdapter;
import com.cjy.notebook.config.Common;
import com.cjy.notebook.database.DBOperate;
import com.cjy.notebook.object.Notes;
import com.cjy.notebook.utils.DialogUtils;
import com.cjy.notebook.service.IService;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint({ "NewApi", "HandlerLeak", "ShowToast", "SimpleDateFormat" })
public class NoteListFragment extends Fragment implements OnItemClickListener {

	private NotesAdapter adapter;
	private ListView NList;
	private DialogUtils dialog;
	private MyBroadCast broadcast;
	private Intent mServiceIntent;
	private int count = 0;// 计数器 每次递增15
	private List<Notes> notesList;
	private static ScheduledExecutorService mScheduleService;// 线程管理指定线程数量可复用线程如不指定则为默认

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_note_list, container,
				false);
		NList = (ListView) view.findViewById(R.id.NList);
		NList.setOnItemClickListener(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Common.DATABASE_DELTETABLE_FROM_NOTES);
		broadcast = new MyBroadCast();
		getActivity().registerReceiver(broadcast, filter);
		notesList = new ArrayList<Notes>();
		show("数据加载中请稍后......");
		init();
		return view;
	}

	public void init() {
		for (int i = count; i < (count + 15); i++) {
			Notes note = new Notes();
			note.setNoteid("CJY : " + i);
			note.setTitle("CJY NOTE TITLE");
			note.setContent("CJY NOTE CONTENT " + (i + 1));
			note.setTime_create(getCurrentTime());
			DBOperate.insertNote(note);
		}
		count += 15;
		getNotesList();
	}

	public String getCurrentTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public void show(String prompt) {
		dialog = new DialogUtils(prompt);
		dialog.show(getActivity().getFragmentManager(), "");
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void getNotesList() {
		mScheduleService = Executors.newSingleThreadScheduledExecutor();
		mScheduleService.submit(new MyRunnable());
	}

	/**
	 * 异步线程 获取数据
	 * */
	public class MyRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			notesList = DBOperate.getNotesList();
			handler.sendEmptyMessage(Common.LOADER_SUCCESS);
		}
	};

	public void deleteAll() {
		for (int i = 0; i < notesList.size(); i++) {
			DBOperate.deleteNote(notesList.get(i));
		}
	}

	public Handler handler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case Common.LOADER_SUCCESS:
				if (notesList == null || notesList.size() < 0) {
					Toast.makeText(getActivity().getApplicationContext(),
							"数据库中已无数据。。。。", Toast.LENGTH_SHORT).show();
					return;
				}
				adapter = new NotesAdapter(getActivity()
						.getApplicationContext(), notesList);
				NList.setAdapter(adapter);
				dismiss();
				break;
			case Common.UPDATE:
				show("数据加载中请稍后......");
				init();
				break;

			case Common.DELETE:
				show("正在清理缓存");
				new Thread() {
					@Override
					public void run() {
						deleteAll();
						handler.sendEmptyMessage(Common.REFRESH);
					}
				}.start();
				break;

			case Common.DELETE_ON_BACKGROUND:
				mServiceIntent = new Intent();
				mServiceIntent.setClass(getActivity(), IService.class);
				Bundle bundle = new Bundle();
				bundle.putInt("cmb", Common.CMB_DELETETABLE);
				mServiceIntent.setAction("");
				mServiceIntent.putExtras(bundle);
				getActivity().startService(mServiceIntent);
				break;
			case Common.REFRESH:
				notesList.removeAll(notesList);
				adapter.notifyDataSetChanged();
				count = 0;
				dismiss();
				break;
			}

		};
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Toast.makeText(getActivity().getApplicationContext(),
				"第" + (position + 1) + "被点击", 0).show();

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (mServiceIntent != null) {
			getActivity().stopService(mServiceIntent);
		}
		getActivity().unregisterReceiver(broadcast);
	}

	/** 接收服务端出来的消息 广播 **/
	class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction()
					.equals(Common.DATABASE_DELTETABLE_FROM_NOTES)) {
				Toast.makeText(getActivity().getApplicationContext(),
						intent.getExtras().getString("result"),
						Toast.LENGTH_SHORT).show();
				show("正在刷新数据.....");
				count = 0;
				getNotesList();
			}
		}
	}

}
