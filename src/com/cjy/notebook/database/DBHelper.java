package com.cjy.notebook.database;

/**
 * @author chenjiayou
 * @feature 数据库
 * @createTime: 2014.11.3
 * @category: CJY Studio
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DBNAME = "CJY NOTEDATABASE";
	private static final String TABLENAME= "CJY_NOTEBOOK";
	private static final String NOTEBOOK = "cjy_notebook";
	private static final int VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLENAME + "" +
			"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			NOTEBOOK + "_id INTEGER(20)," +
			NOTEBOOK + "noteid INTEGER(20),"+
			NOTEBOOK + "time_create VARCHAR(50)," +
			NOTEBOOK + "time_change VARCHAR(50)," +
			NOTEBOOK + "title VARCHAR(50),"+
			NOTEBOOK + "content TEXT)";
	
	
	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	
}
