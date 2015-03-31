package com.cjy.notebook.database;

/**
 * @author chenjiayou
 * @feature 数据库管理类
 * @createTime: 2014.11.3
 */
import java.util.concurrent.atomic.AtomicInteger;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private AtomicInteger mOpenCounter = new AtomicInteger();
	private static DBHelper mDatabaseHelper;
	private static DBManager instance;
	private SQLiteDatabase mDatabase;
	
	private DBManager(){
		
	}
	
	/**
	 * 初始化
	 * */
	public static synchronized void initializeInstance(Context context) {
		if (instance == null) {
			instance = new DBManager();
			mDatabaseHelper = new DBHelper(context);
		}
	}

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			// Opening new database
			mDatabase = mDatabaseHelper.getWritableDatabase();
		}
		return mDatabase;
	}

	/**
	 * 当调用此方法为最后一个线程时 可以关闭数据库了
	 * */
	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			// Closing database
			mDatabase.close();
		}
	}
}
