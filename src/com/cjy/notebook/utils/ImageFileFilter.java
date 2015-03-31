package com.cjy.notebook.utils;

import java.io.File;
import java.io.FileFilter;


public class ImageFileFilter implements FileFilter {

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		String filename = file.getName();
		if(filename.endsWith(".png") || filename.endsWith(".jpg"))
			return true;
		else
			return false;
	}
	
}
