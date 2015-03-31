package com.cjy.notebook.database;


/**
 * @author chenjiayou
 * @feature 数据库操作辅助类
 * @createTime: 2014.11.3
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cjy.notebook.object.Notes;

public class DBOperate {

	private static final String TABLENAME= "CJY_NOTEBOOK";
	private static final String NOTEBOOK = "cjy_notebook";
	
	public static List<Notes> getNotesList() {
		List<Notes> list = new ArrayList<Notes>();
		SQLiteDatabase db = DBManager.getInstance().openDatabase();
		Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
		while(cursor != null && cursor.moveToNext()){
			Notes note = new Notes();
			note.setId(cursor.getString(cursor.getColumnIndex("_id")));
			note.setNoteid(cursor.getString(cursor.getColumnIndex(NOTEBOOK+"_id")));
			note.setTime_create(cursor.getString(cursor.getColumnIndex(NOTEBOOK+"time_create")));
			note.setTime_change(cursor.getString(cursor.getColumnIndex(NOTEBOOK+"time_change")));
			note.setTitle(cursor.getString(cursor.getColumnIndex(NOTEBOOK+"title")));
			note.setContent(cursor.getString(cursor.getColumnIndex(NOTEBOOK+"content")));
			list.add(note);
		}
		DBManager.getInstance().closeDatabase();
		return list;
	}

	public static void insertNote(Notes note){
		SQLiteDatabase db = DBManager.getInstance().openDatabase();
		ContentValues values = new ContentValues();
		values.put(NOTEBOOK+"_id", note.getNoteid());
		values.put(NOTEBOOK+"time_create", note.getTime_create());
		values.put(NOTEBOOK+"time_change", note.getTime_change());
		values.put(NOTEBOOK+"title", note.getTitle());
		values.put(NOTEBOOK+"content", note.getContent());
		db.insert(TABLENAME, "_id", values);
		DBManager.getInstance().closeDatabase();
	}
	
	public static void deleteNote(Notes note){
		SQLiteDatabase db = DBManager.getInstance().openDatabase();
//		String sql = "delete from "+ TABLENAME + " where"+NOTEBOOK+"_id = " + note.getNoteid();
//		db.execSQL(sql);
		db.delete(TABLENAME, NOTEBOOK+"_id = ? " , new String[]{note.getNoteid()});
		DBManager.getInstance().closeDatabase();
	}
	
	/**删除表中所有数据*/
	public static void deleteTable(){
		SQLiteDatabase db = DBManager.getInstance().openDatabase();
		String delete_sequence = "UPDATE sqlite_sequence SET seq = 0 WHERE name = " + TABLENAME;//自增列清空 归零
//		String delete_sequence = "DELETE FROM sqlite_sequence WHERE name = " + TABLENAME;//自增列清空 归零
		String delete_table = "DELETE FROM " + TABLENAME;//清空表中所有数据
		db.execSQL(delete_table);
//		db.execSQL(delete_sequence);
		DBManager.getInstance().closeDatabase();
	}
	
	public static void updateNote(Notes note){
		SQLiteDatabase db = DBManager.getInstance().openDatabase();
		ContentValues values = new ContentValues();
		values.put(NOTEBOOK+"_id", note.getNoteid());
		values.put(NOTEBOOK+"time_create", note.getTime_create());
		values.put(NOTEBOOK+"time_change", note.getTime_change());
		values.put(NOTEBOOK+"title", note.getTitle());
		values.put(NOTEBOOK+"content", note.getContent());
		db.update(TABLENAME, values, NOTEBOOK+"_id = ", new String[]{note.getNoteid()});
		DBManager.getInstance().closeDatabase();
	}
}
