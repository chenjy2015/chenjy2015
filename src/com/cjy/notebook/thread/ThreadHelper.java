package com.cjy.notebook.thread;

import java.util.ArrayList;
import java.util.List;

import android.os.Looper;

import com.cjy.notebook.database.DBOperate;

public class ThreadHelper {

	private static ThreadHelper instance;
	protected List<Runnable> RQList;
	private DeleteRunnable deleteRunnable;
	private RunnableCallback callback;
	private ThreadHelper(){
		
	}
	
	public static ThreadHelper getInstance(){
		if(instance == null){
			instance = new ThreadHelper();
		}
		return instance;
	}
	
	public void newRunnable(RunnableCallback callback){
		RQList = new ArrayList<Runnable>();
		deleteRunnable = new DeleteRunnable();
		RQList.add(deleteRunnable);
		this.callback = callback;
		new Thread(deleteRunnable).start();
	}
	
	private class DeleteRunnable implements Runnable{

		public DeleteRunnable(){
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			DBOperate.deleteTable();
			callback.onSuccess("线程已执行完成");
		}
	}
	
	public interface RunnableCallback{
		public void onSuccess(String result);
	}
}
