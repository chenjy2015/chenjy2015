package com.cjy.notebook.utils;


/**
 * @author chenjiayou
 * @feature NotesListActivity 主界面
 * @createTime: 2014.11.3
 * 
 * 需要在Application中配置 
 * ImageLoaderUtils.mContext = getApplicationContext;
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import utils.ImageGetFromHttp;
import utils.ImageGetFromHttp.onDownLoadListnner;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjy.notebook.R;
import com.cjy.notebook.utils.ImageFileCache.loaderFileCacheListenner;

public class ImageLoaderUtils {

	private static ImageMemoryCache memoryCache;
	private static ImageFileCache fileCache;
	private static Context mContext;
	private static ScheduledExecutorService mScheduleService;// 线程管理指定线程数量可复用线程如不指定则为默认
	private static ImageLoaderUtils mInstance;

	public static ImageLoaderUtils getInstance(Context context) {
		mContext = context;
		if (mInstance == null) {
			mInstance = new ImageLoaderUtils();
		}
		return mInstance;
	}

	private ImageLoaderUtils() {
		memoryCache = new ImageMemoryCache(mContext);
		fileCache = new ImageFileCache();
		mScheduleService = Executors.newScheduledThreadPool(3);
	}

	public void ImageLoader(String url, ImageView iv) {
		iv.setImageDrawable(mContext.getResources().getDrawable(
				R.drawable.ic_launcher));
		getBitmap(url, iv);
	}

	/**
	 * 先从内存中获取 如果为空 从文件中获取并压缩图片 如果为空 从网络下载 并压缩图片 加载到内存中
	 * */
	private void getBitmap(String url, final ImageView iv) {
		// 从内存缓存中获取图片
		String key = MD5.getMD5String(url);
		Bitmap result = memoryCache.getBitmapFromCache(key);
		if (result != null) {
			// if (CheckTag(url, iv)) {
			iv.setImageBitmap(result);
			return;
			// }
			// return;
		} else {
			mScheduleService.submit(new MyRunnable(url, iv));
		}
	}

	/***
	 * 开启线程 从文件中读取图片
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyRunnable implements Runnable {

		private String url;
		private ImageView iv;

		public MyRunnable(String url, ImageView iv) {
			this.url = url;
			this.iv = iv;
		}

		@Override
		public void run() {
			getImageFromFile(url, iv);
		}
	};

	/**
	 * 从文件中获取数据 如果为空则从网络中下载
	 * */
	private void getImageFromFile(String url, ImageView iv) {

		String filename = fileCache.convertUrlToFileName(url);
		String dir = fileCache.getDirectory();
		String filePath = fileCache.getFilePath(dir, filename);
		Bitmap bm = fileCache.getImg(url, iv, null);
		if (bm != null) {
			iv.setImageBitmap(bm);
			String key = MD5.getMD5String(url);
			memoryCache.addBitmapToCache(key, bm);

		} else {
			getImageFromHttp(url, iv);
		}
	}

	/**
	 * 从网络中获取数据
	 * */
	private void getImageFromHttp(String url, final ImageView iv) {
		ImageGetFromHttp.getInstance().downloadBitmap(url,
				new onDownLoadListnner() {

					@Override
					public void onDownLoadSuccess(Bitmap bitmap, String url) {
						// TODO Auto-generated method stub
						if (bitmap != null) {
							fileCache.saveBitmap(bitmap, url);
							@SuppressWarnings("static-access")
							String key = MD5.getMD5String(url);
							String filename = fileCache
									.convertUrlToFileName(url);
							String dir = fileCache.getDirectory();
							String filePath = fileCache.getFilePath(dir,
									filename);
							// 通过对图片的压缩处理后得到的bitmap对象 存放到内存缓存中
							Bitmap bm = fileCache.getImage(filePath,
									iv.getWidth());
							memoryCache.addBitmapToCache(key, bm);
							iv.setImageBitmap(bitmap);
						}
					}
				});
	}

	/**
	 * 检测图片的Tag值是否相同
	 * */
	private boolean CheckTag(String tag, ImageView iv) {
		if (iv == null || tag == null) {
			return false;
		}
		if (tag.equals(iv.getTag())) {
			return true;
		}
		return false;
	}

}
