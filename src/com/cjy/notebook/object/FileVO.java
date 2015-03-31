package com.cjy.notebook.object;

import java.io.Serializable;

public class FileVO implements Serializable {

	private static final long serialVersionUID = 1L;

	String name;
	String path;
	float length;
	long lastModified;

	public FileVO() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long l) {
		this.lastModified = l;
	}

}
