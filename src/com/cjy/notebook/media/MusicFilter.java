package com.cjy.notebook.media;

import java.io.File;
import java.io.FilenameFilter;

public class MusicFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		
		if(name.endsWith(".mp3") || name.endsWith(".m4a")){
			return true;
		}else{
			return false;
		}
	}

}
