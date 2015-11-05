package com.cjy.notebook.widget.dialog.helper;

/**
 * 对话框中按钮类型 
 */
public enum DialogButtonType {

	OK("OK"),
	NO("NO");
	
	String type;
	
	private DialogButtonType(String type){
		this.type = type;
	}
}
