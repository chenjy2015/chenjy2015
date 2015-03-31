package com.cjy.notebook.viewholder;

import android.widget.TextView;

public class NotesViewHolder {

	public TextView title;
	public TextView content;
	public TextView time_create;
	public TextView time_change;
	
	
	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public TextView getContent() {
		return content;
	}

	public void setContent(TextView content) {
		this.content = content;
	}

	public TextView getTime_create() {
		return time_create;
	}

	public void setTime_create(TextView time_create) {
		this.time_create = time_create;
	}

	public TextView getTime_change() {
		return time_change;
	}

	public void setTime_change(TextView time_change) {
		this.time_change = time_change;
	}



}
