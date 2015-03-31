package com.cjy.notebook.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MusicVO implements Serializable {

	private String filename;
	private String filePath;
	private long lenght;

	public MusicVO() {

	}

	public MusicVO(String filename, String filePath, long lenght) {
		super();
		this.filename = filename;
		this.filePath = filePath;
		this.lenght = lenght;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getLenght() {
		return lenght;
	}

	public void setLenght(long lenght) {
		this.lenght = lenght;
	}

}
