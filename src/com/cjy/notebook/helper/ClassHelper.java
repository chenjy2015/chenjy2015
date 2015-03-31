package com.cjy.notebook.helper;

import java.util.ArrayList;

import com.cjy.notebook.object.MusicVO;

import android.content.Context;

public interface ClassHelper {

	/**获取当前文件夹最合适的路径位置*/
	public String getFitSdPath(Context mContext);
	
	/**设置当前最合适的存储路径*/
	public void saveFitSdPath(Context mContext , String path);
	
	/***/
	public ArrayList<MusicVO> getMusicList();
	
	/***/
	public void getfile(String filePath);
	
	/**截取文件路径后半部分*/
	public String getFileName(String headPath,String filePath);
	
}
