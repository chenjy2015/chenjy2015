package com.cjy.notebook.utils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

public class ImageMemoryCache {

	private LruCache<String, Bitmap> mLruCache;
	private LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache;

	// LinkedHashMap初始容量
	private static final int SOFT_CACHE_SIZE = 16;
	// LinkedHashMap加载因子
	private static final float LOAD_FACTOR = 0.75f;
	// LinkedHashMap排序模式
	private static final boolean ACCESS_ORDER = true;

	@SuppressWarnings("serial")
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public ImageMemoryCache(Context context) {
//		int memClass = (int) Runtime.getRuntime().maxMemory();
		int memClass = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 4;// 强引用的容量为系统可用内存的1/4
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 计算存储bitmap所占用的字节数
				if (value != null) {
					return value.getRowBytes() * value.getHeight();
				} else {
					return 0;
				}

			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null) {
					// 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
					mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
				}
			}
		};

		/*
		 * 第一个参数：初始容量（默认16） 第二个参数：加载因子（默认0.75）
		 * 第三个参数：排序模式（true：按访问次数排序；false：按插入顺序排序）
		 */
		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
				SOFT_CACHE_SIZE, LOAD_FACTOR, ACCESS_ORDER) {
			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				// 判断当前缓存容量是否超出 如果是则开始删除部分数据
				if (size() > SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * 先从强引用中取 如果没有则从软引用中取 如果还没有 就去文件中取 实在没有就去网络中取
	 * 
	 * */
	@SuppressLint("NewApi")
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap;

		// 先从强引用中取
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				// 找到该Bitmap之后，将其移到LinkedHashMap的最前面，保证它在LRU算法中将被最后删除。
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				return bitmap;
			}
		}

		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			// 找到该Bitmap之后，将它移到硬引用缓存。并从软引用缓存中删除。
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					mLruCache.put(url, bitmap);
					mSoftCache.remove(url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}

		return null;

	}

	
	/**
	 * 添加Bitmap到内存缓存
	 */
	@SuppressLint("NewApi")
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}

	/**
	 * 清理软引用缓存
	 */
	public void clearCache() {
		mSoftCache.clear();
		mSoftCache = null;
	}
}
