package com.cjy.notebook.utils;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

import com.cjy.notebook.object.FileVO;

public class FileUtils {

	public static ArrayList<FileVO> list;
	
	public static void init(){
		list = new ArrayList<FileVO>();
	}
	
	public static ArrayList<FileVO> getFileList() {
		init();
		if(!SDCard.isExistSDCard()){
			return list;
		}
		File parentfile = Environment.getExternalStorageDirectory();
		scannerFile(parentfile);
		return list;
	};
	
	/**递归扫描文件夹*/
	public static void scannerFile(File file){
		if(file == null){
			return;
		}
		
		File[] files = file.listFiles();
		if(files != null && files.length > 0){
			for(File f:files){
				if(f.isDirectory()){
					scannerFile(f);
				}else if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg")){
					FileVO fileVO = new FileVO();
					fileVO.setName(f.getName());
					fileVO.setPath(f.getAbsolutePath());
					fileVO.setLength(f.length());
					fileVO.setLastModified(f.lastModified());
					list.add(fileVO);
				}
			}
		}
	}
	
//	public static String splitFileName(String filePath){
//		//filePath.replace("", newChar)
//	}
	
	
}
