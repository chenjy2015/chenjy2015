package com.cjy.notebook.media;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cjy.notebook.R;

public class VideoActivity1 extends Activity implements SurfaceHolder.Callback {

	private Button mStartButton;
	private Button mStopButton;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private MediaRecorder mMediaRecorder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置横屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_video1);
		init();
	}

	private void init() {
		mStartButton = (Button) findViewById(R.id.start_button);
		mStartButton.setOnClickListener(new ButtonClickListenerImpl());
		mStopButton = (Button) findViewById(R.id.stop_button);
		mStopButton.setOnClickListener(new ButtonClickListenerImpl());
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private void initMediaRecorder() {
		// next codes is right for 2.3 and 4.0
		mMediaRecorder = new MediaRecorder();
		// 设置视频源
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		// 设置音频源
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		// 设置文件输出格式
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// 设置视频编码方式
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
		// 设置音频编码方式
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// 设置视频高和宽,注意文档的说明:
		// Must be called after setVideoSource().
		// Call this after setOutFormat() but before prepare().
		// 设置录制的视频帧率,注意文档的说明:
		// Must be called after setVideoSource().
		// Call this after setOutFormat() but before prepare().
		mMediaRecorder.setVideoFrameRate(15);
		// 设置预览画面
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		// 设置输出路径
		mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory()
				+ File.separator + System.currentTimeMillis() + ".mp4");
		mMediaRecorder.setVideoSize(800, 480);
		// 设置视频的最大持续时间
		mMediaRecorder.setMaxDuration(10000);
		// 为MediaRecorder设置监听
		mMediaRecorder.setOnInfoListener(new OnInfoListener() {
			public void onInfo(MediaRecorder mr, int what, int extra) {
				if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
					System.out.println("已经达到最长录制时间");
					if (mMediaRecorder != null) {
						mMediaRecorder.stop();
						mMediaRecorder.release();
						mMediaRecorder = null;
					}
				}

			}
		});
	}

	private class ButtonClickListenerImpl implements OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.start_button) {
				try {
					initMediaRecorder();
					mMediaRecorder.prepare();
					mMediaRecorder.start();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (v.getId() == R.id.stop_button) {
				if (mMediaRecorder != null) {
					mMediaRecorder.stop();
					mMediaRecorder.release();
					mMediaRecorder = null;
				}
			}
		}
	}

	// SurfaceHolder.Callback接口
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("SurfaceView---->Created");
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out.println("SurfaceView---->Changed");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("SurfaceView---->Destroyed");
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

}
