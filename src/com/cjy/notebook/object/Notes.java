package com.cjy.notebook.object;

import java.io.Serializable;

/**
 * @author chenjiayou
 * @feature Note对象
 * @createTime: 2014.11.4
 * @category: CJY Studio
 * @parmas:id          存储在数据库中的_id值
 * @parmas:noteid      笔记对象的ID值
 * @parmas:time_create 笔记创建时间
 * @parmas:time_change 笔记修改时间
 * @parmas:title       笔记标题
 * @parmas:content     笔记正文
 */

public class Notes implements Serializable{
	
	private String id;
	private String noteid;
	private String time_create;
	private String time_change;
	private String title;
	private String content;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoteid() {
		return noteid;
	}
	public void setNoteid(String noteid) {
		this.noteid = noteid;
	}
	public String getTime_create() {
		return time_create;
	}
	public void setTime_create(String time_create) {
		this.time_create = time_create;
	}
	public String getTime_change() {
		return time_change;
	}
	public void setTime_change(String time_change) {
		this.time_change = time_change;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
}
