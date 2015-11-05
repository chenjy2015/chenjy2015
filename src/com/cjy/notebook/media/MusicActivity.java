package com.cjy.notebook.media;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.cjy.notebook.R;
import com.cjy.notebook.adapter.MusicListAdapter;
import com.cjy.notebook.helper.ClassHelperUtils;
import com.cjy.notebook.object.MusicVO;
import com.cjy.notebook.utils.DialogUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MusicActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	/** MediaPlayer 对象 */
	private MediaPlayer mPlayer;
	/** 音乐列表 */
	private ArrayList<MusicVO> mMusicList;
	/** 当前播放音乐索引 */
	private int currentIndex = 0;

	private File mHomeFile;
	private Button mFront; // 上一首
	private Button mStop; // 停止
	private Button mStart; // 开始
	private Button mPause; // 暂停
	private Button mNext; // 下一首

	private ListView mMusicListView;
	private DialogUtils dialog;
	private MusicListAdapter adapter;
	private ClassHelperUtils utils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		utils = new ClassHelperUtils();
		
		/** 构建Media对象 */
		mPlayer = new MediaPlayer();
		mMusicListView = (ListView) findViewById(R.id.music_list);
		mMusicListView.setOnItemClickListener(this);
		mFront = (Button) findViewById(R.id.front);
		mStop = (Button) findViewById(R.id.stop);
		mStart = (Button) findViewById(R.id.start);
		mPause = (Button) findViewById(R.id.pause);
		mNext = (Button) findViewById(R.id.next);
		mFront.setOnClickListener(this);
		mStop.setOnClickListener(this);
		mStart.setOnClickListener(this);
		mPause.setOnClickListener(this);
		mNext.setOnClickListener(this);
		
		showHint();
		initMusicList();
	}

	public Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			dismiss();
			if (mMusicList == null || mMusicList.size() < 1) {
				return;
			}
			adapter = new MusicListAdapter(getApplicationContext(), mMusicList);
			mMusicListView.setAdapter(adapter);
		};
	};

	private void showHint() {
		dialog = new DialogUtils("正在扫描音乐");
		dialog.show(getFragmentManager(), "");
	}

	private void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	/** 获取音乐列表路径 */
//	private void initFilePath() {
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			mHomeFile = Environment.getExternalStorageDirectory();
//		} else {
//			mHomeFile = new File("/sdcard/");
//		}
//	}

	private void initMusicList() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				initFilePath();
//				mMusicList = new ArrayList<MusicVO>();
//				if (mHomeFile != null && mHomeFile.exists()) {
//					/** 过滤文件后的子文件数量超过0 */
//					if (mHomeFile.listFiles(new MusicFilter()).length > 0) {
//						for (File file : mHomeFile.listFiles(new MusicFilter())) {
//							MusicVO m = new MusicVO();
//							m.setFilePath(file.getAbsolutePath());
//							m.setFilename(getFileName(file.getAbsolutePath()));
//							m.setLenght(m.getLenght());
//							mMusicList.add(m);
//						}
//					}
//				}
				mMusicList = utils.getMusicList();
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

//	private String getFileName(String filePath){
//		return filePath.replace(mHomeFile.getAbsolutePath().toString()+"/", "");
//	}
	
	/** 上一首 */
	public void frontMusic() {
		currentIndex--;
		if (currentIndex <= 0) {
			currentIndex = (mMusicList.size() - 1);
		}
	}

	/** 停止 */
	public void stopMusic() {
		if (mPlayer.isPlaying()) {
			mPlayer.reset();
		}
	}

	/** 播放音乐 */
	public void startMusic() {
		try {
			mPlayer.reset();
			//mPlayer.setAudioStreamType(AudioSource.MIC);
			/** 设置播放文件的路径 */
			mPlayer.setDataSource(mMusicList.get(currentIndex).getFilePath());
			/** 准备播放 */
			mPlayer.prepare();
			/** 开始播放 */
			mPlayer.start();
			/** 播放进度监听 */
			mPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					nextMusic();
				}
			});

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 暂停 */
	public void pauseMusic() {
		if (mPlayer.isPlaying()) {
			mPlayer.pause();
		} else {
			mPlayer.start();
		}
	}

	/** 下一首 */
	public void nextMusic() {
		currentIndex++;
		if (currentIndex >= mMusicList.size()) {
			currentIndex = 0;
		}
		startMusic();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mPlayer.stop();
			mPlayer.release();
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub
		currentIndex = position;
		startMusic();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.front:
			frontMusic();
			break;

		case R.id.stop:
			stopMusic();
			break;

		case R.id.start:
			startMusic();
			break;

		case R.id.pause:
			pauseMusic();
			break;
		case R.id.next:
			nextMusic();
			break;

		}
	}

}
