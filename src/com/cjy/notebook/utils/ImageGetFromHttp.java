package com.cjy.notebook.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageGetFromHttp {

	private static List<Runnable> runableList;
	private static final int poolSize = 6;// 线程多开启数量
	private static ScheduledExecutorService mScheduleService;//线程管理指定线程数量可复用线程如不指定则为默认
	private static ImageGetFromHttp mInstance;
	private static final String LOG_TAG = "ImageGetFromHttp";
	private Bitmap bm;

	private ImageGetFromHttp() {
		runableList = new ArrayList<Runnable>();
		mScheduleService = Executors.newSingleThreadScheduledExecutor();
	}

	public static ImageGetFromHttp getInstance() {
		if (mInstance == null) {
			mInstance = new ImageGetFromHttp();
		}
		return mInstance;
	}

//	public Bitmap downloadBitmap(String url) {
//		// 产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，若任务数量大于poolSize，任务会在一个queue里等待执行
//		mScheduleService.submit(new MyRunnable(url, new onDownLoadListnner() {
//
//			@Override
//			public void onDownLoadSuccess(Bitmap bitmap) {
//				// TODO Auto-generated method stub
//				bm = bitmap;
//			}
//		}));
//		mScheduleService.shutdown();
//		return bm;
//	}
	public void downloadBitmap(String url,onDownLoadListnner listenner) {
		// 产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，若任务数量大于poolSize，任务会在一个queue里等待执行
		mScheduleService.submit(new MyRunnable(url, listenner));
		//mScheduleService.shutdown();
		//new Thread(new MyRunnable(url, listenner)).start();
	}

	private class MyRunnable implements Runnable {
		String url;
		onDownLoadListnner downLoaderListenner;

		public MyRunnable(String url, onDownLoadListnner downLoaderListenner) {
			this.url = url;
			this.downLoaderListenner = downLoaderListenner;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			bm = getBitmap(url);
			downLoaderListenner.onDownLoadSuccess(bm,url);
		}

	}

	public interface onDownLoadListnner {
		public void onDownLoadSuccess(Bitmap bitmap, String url);
	};

	public Bitmap getBitmap(String url) {
		Bitmap bm = null;
		final HttpClient client = new DefaultHttpClient();
		final HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(LOG_TAG, "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream in = null;
				try {
					in = entity.getContent();
					FilterInputStream fit = new FlushedInputStream(in);
					return bm = BitmapFactory.decodeStream(in);
				} finally {
					if (in != null) {
						in.close();
						in = null;
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		mScheduleService.shutdown();
		return bm;
	}

	/**
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 * 
	 * Android对于InputStream流有个小bug在慢速网络的情况下可能产生中断，
	 * 可以考虑重写FilterInputStream处理skip方法来解决这个bug。
	 * BitmapFactory类的decodeStream方法在网络超时或较慢的时候无法获取完整的数据，这里我
	 * 们通过继承FilterInputStream类的skip方法来强制实现flush流中的数据
	 * ，主要原理就是检查是否到文件末端，告诉http类是否继续。
	 */
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

}
