package com.cjy.notebook.media;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.cjy.notebook.config.Common;
import com.cjy.notebook.global.CJYApplication;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

	private Camera mCamera;
	private Parameters parameter;
	private SurfaceHolder mSurfaceHolder;
	private MediaRecorder mMediaRecorder;
	private StartBroadCast mBroadCast;
	private ScheduledExecutorService  mExectuor;//java 5.0 线程池管理服务类
	private Context mContext;
	
	@SuppressWarnings("deprecation")
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		//initMediaRecorder();
		registerBroadCast();
		mExectuor = Executors.newScheduledThreadPool(3);//初始化线程池数量
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	private void registerBroadCast(){
		mBroadCast = new StartBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Common.VIDEO_START_PLAYER);
		filter.addAction(Common.VIDEO_STOP_PLAYER);
		filter.addAction(Common.VIDEO_START_RECORDING);
		filter.addAction(Common.VIDEO_STOP_RECORDING);
		//filter.addAction(Common.ACTION_STOP_MEDIA_ACTIVITY);
		getContext().registerReceiver(mBroadCast, filter);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mSurfaceHolder = holder;
		
		/** 打开相机 **/
		mCamera = Camera.open();
		try {
			/** 表示将相机拍照的预览界面放在Holder对象中 **/
			mCamera.setPreviewDisplay(mSurfaceHolder);
			/** 旋转90预览 */
			mCamera.setDisplayOrientation(90);
//			/** 自动对焦*/
//			mCamera.autoFocus(this);
			/** 启动预览 */
			mCamera.startPreview();
		} catch (IOException e) {
			/** 如果有异常 那么记得回收相机 不然下次打开相机会报错 **/
			mCamera.release();
			mCamera = null;
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (mMediaRecorder!=null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder=null;
			mCamera.release();
			mCamera = null;
			mExectuor.shutdown();
		}
	}

	public void openLight() {
		if (mCamera != null) {
			parameter = mCamera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(parameter);
		}
	}

	public void offLight() {
		if (mCamera != null) {
			parameter = mCamera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(parameter);
		}
	}
	
	
//	private void initMediaRecorder(){
//    	//next codes is right for 2.3 and 4.0
//    	mMediaRecorder=new MediaRecorder();
//    	//设置视频源
//    	mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
//    	//设置音频源
//    	mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
//    	//设置文件输出格式
//    	mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//    	//设置视频编码方式
//    	mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
//    	//设置音频编码方式
//    	mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//    	//设置视频高和宽,注意文档的说明:
//    	//Must be called after setVideoSource().
//    	//Call this after setOutFormat() but before prepare().
//    	mMediaRecorder.setVideoSize(800, 480);
//    	//设置视频的最大持续时间
//    	mMediaRecorder.setMaxDuration(10000);
//    	//设置录制的视频帧率,注意文档的说明:
//    	//Must be called after setVideoSource().
//    	//Call this after setOutFormat() but before prepare().
//    	mMediaRecorder.setVideoFrameRate(15);
//    	//设置预览画面
//    	mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
//    	//设置输出路径
//    	mMediaRecorder.setOutputFile(getParentFilePath()+File.separator+System.currentTimeMillis()+".mp4");
//    	//为MediaRecorder设置监听
//    	mMediaRecorder.setOnInfoListener(new OnInfoListener() {
//			public void onInfo(MediaRecorder mr, int what, int extra) {
//				if (what==MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
//					System.out.println("已经达到最长录制时间");
//					Toast.makeText(getContext(), "已经达到最长录制时间",Toast.LENGTH_SHORT).show();
//					stopRecording();
//				}else{
//					
//				}
//			}
//		});
//    }
	
	@SuppressLint("NewApi")
	private void initMediaRecorder(){
		  CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);  
		  //1st. Initial state  
		  mMediaRecorder = new MediaRecorder();  
		  mCamera.unlock();
		  mMediaRecorder.setCamera(mCamera);  
		  //2st. Initialized state  
		  mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		  mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); 
		  //3st. config  
	      mMediaRecorder.setOutputFormat(mProfile.fileFormat);  
	      mMediaRecorder.setAudioEncoder(mProfile.audioCodec);  
	      mMediaRecorder.setVideoEncoder(mProfile.videoCodec);  
	      String pathname = File.separator+System.currentTimeMillis()+".mp4";
	      mMediaRecorder.setOutputFile(getParentFilePath()+pathname);  
	      mMediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);  
	      mMediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);  
	      mMediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);  
	      mMediaRecorder.setAudioEncodingBitRate(mProfile.audioBitRate);  
	      mMediaRecorder.setAudioChannels(mProfile.audioChannels);  
	      mMediaRecorder.setAudioSamplingRate(mProfile.audioSampleRate);  
	      mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());  
		
	}
	
	private void startRecording(){
		if(mMediaRecorder==null){
			initMediaRecorder();
		}
			try {
				mMediaRecorder.reset();
				mMediaRecorder.prepare();
				mMediaRecorder.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Log.e("MediaRecorder start fail ", e.getMessage()+"");
				if (mMediaRecorder!=null) {
					mMediaRecorder.stop();
					mMediaRecorder.release();
					mMediaRecorder=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("MediaRecorder start fail ", e.getMessage());
				if (mMediaRecorder!=null) {
					mMediaRecorder.stop();
					mMediaRecorder.release();
					mMediaRecorder=null;
				}
			}
		}
	
	private void stopRecording(){
		if (mMediaRecorder!=null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder=null;
		}
	}
	
	
	/**
	 * 接受主界面发送的广播信息
	 * @author chenjiayou
	 *
	 */
	private class StartBroadCast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(Common.VIDEO_START_RECORDING)){
//				mExectuor.submit(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						startRecording();
//					}
//				});
				startRecording();
			}else if(intent.getAction().equals(Common.VIDEO_STOP_RECORDING)){
//				mExectuor.submit(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						stopRecording();
//					}
//				});
				stopRecording();
			}
		}
	};
	
	/**
	 * 视频存储路径
	 * @return
	 */
	private String getParentFilePath(){
		String parentPath = CJYApplication.getHelperUtis().getFitSdPath(getContext());
		File file = new File(parentPath+File.separator+"CJY_NOTEBOOK_MEDIA");
		if(!file.exists()){
			file.mkdirs();
		}
		String path = file.getAbsolutePath();
		return path;
	}
}
