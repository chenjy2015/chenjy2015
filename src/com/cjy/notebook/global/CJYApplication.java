package com.cjy.notebook.global;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import com.cjy.notebook.R;
import com.cjy.notebook.database.DBManager;
import com.cjy.notebook.helper.ClassHelperUtils;
import com.cjy.notebook.utils.ImageLoaderUtils;
import com.cjy.notebook.utils.SDCard;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class CJYApplication extends Application {

	@SuppressWarnings("unused")
	private static CJYApplication mInstance;
	public static List<Activity> activityList;//管理所有Activity对象
	public static List<Thread> allThreadList;//管理所有Thread对象
	/**Universal*/
	public static ImageLoaderConfiguration config;
	public static DisplayImageOptions options;
	public static ClassHelperUtils mHelperUtils;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mInstance = this;
		mHelperUtils = new ClassHelperUtils();
		activityList = new ArrayList<Activity>();
		allThreadList = new ArrayList<Thread>();
		//初始化数据库管理类
		DBManager.initializeInstance(getApplicationContext());
		
		/**
		 * init universal
		 * */
		config = ApplicationInit.initConfig(mInstance);
		options = ApplicationInit.initOptions(mInstance);
	}
	
	public static ClassHelperUtils getHelperUtis(){
		return mHelperUtils;
	}
	
	public void initEngineManager(final Context context) {
 		new Thread(new Runnable() {
			@Override
			public void run() {
				initSdCard();
			}
		}).start();
	}
	
	/**
	=======
	 * 判断一下 先做个测试 如果创建多个目录的文件夹成功 则表明版本在4.4以下 可以读取外部SDcard内容 否则
	 * 只能读取手机内部SDCARD存储目录 将最佳路径存放到SharedPreferences中保存
	 */
	@SuppressLint("NewApi")
	public void initSdCard() {
		// 先测试当前系统是否允许操作外部SD卡
 		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			//判断当前android系统版本是否大于4.3
			if (android.os.Build.VERSION.SDK_INT >= 19) {
				File file = Environment.getExternalStorageDirectory();
				mHelperUtils.saveFitSdPath(this, file.getAbsoluteFile()
						.toString());
			}else{
				SDCard.initFitSdcard(this);
			}
		} 
	}
	

	/** 关闭Activity对象 */
	public static void DeleteActivity(Activity activity) {

		if (activityList != null) {
			for (int i = 0; i < activityList.size(); i++) {
				if (activity == activityList.get(i)) {
					activityList.remove(i);
					return;
				}
			}
		}
	}
	
	/**删除所有Activity对象*/
	public static void DeleteAllActivity() {
		if (activityList.size() > 0 && activityList != null) {
			for (int i = 0; i < activityList.size(); i++) {
				Activity activity = activityList.get(i);
				activity.finish();
			}
		}
		activityList.removeAll(activityList);
	}
	
	/**删除所有Thread对象*/
	public static void DeleteAllThread() {
		if (allThreadList.size() > 0 && allThreadList != null) {
			for (int i = 0; i < allThreadList.size(); i++) {
				Thread thread = allThreadList.get(i);
				thread.interrupt();
			}
		}
		allThreadList.removeAll(allThreadList);
	}

	/** 退出系统 */
	public static void ExitApplication() {
		DeleteAllActivity();
		DeleteAllThread();
		activityList = null;
		allThreadList = null;
		System.exit(0);
	}
	

}
