package com.cjy.notebook.helper;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.cjy.notebook.config.Common;
import com.cjy.notebook.media.MusicFilter;
import com.cjy.notebook.object.MusicVO;
import com.cjy.notebook.utils.SP;

public class ClassHelperUtils implements ClassHelper {

	private static File mPFile;
	private static ArrayList<MusicVO> data;
	
	@Override
	public String getFitSdPath(Context mContext) {
		return SP.getDefaultSharedPreferences(mContext).getString(
				Common.DEFAUT_CJY_STORAGEPATH,
				Environment.getExternalStorageDirectory().getPath());
	}
	
	public void saveFitSdPath(Context mContext , String path){	
		SharedPreferences.Editor editor = SP.getDefaultSharedPreferences(mContext).edit();
		editor.putString(Common.DEFAUT_CJY_STORAGEPATH, path);
		editor.commit();
		editor.clear();
	}	
	
	public ArrayList<MusicVO> getMusicList(){
		data = new ArrayList<MusicVO>();
		File parentPath1;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			parentPath1 = Environment.getExternalStorageDirectory();
		}else{
			parentPath1 = Environment.getRootDirectory();
		}
		mPFile = parentPath1;
		getfile(parentPath1.getAbsolutePath());
		return data;
	}
	
	public void getfile(String filePath){
		File file = new File(filePath);
		File[] files = file.listFiles();
		if(files == null || files.length < 1){
			return;
		}
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				getfile(files[i].getAbsolutePath());
			}else{
				if(isMusic(files[i].getAbsolutePath())){
					MusicVO m = new MusicVO();
					m.setFilename(getFileName(mPFile.getAbsolutePath(),files[i].getAbsolutePath()));
					m.setFilePath(files[i].getAbsolutePath());
					m.setLenght(files[i].length());
					data.add(m);
				}
			}
		}
	}
	
	public String getFileName(String headPath,String filePath){
		return filePath.replace(headPath+"/", "");
	}
	
	public boolean isMusic(String fileName){
		if(fileName.endsWith(".mp3") || fileName.endsWith(".m4a")){
			return true;
		}else{
			return false;
		}
	}
}
