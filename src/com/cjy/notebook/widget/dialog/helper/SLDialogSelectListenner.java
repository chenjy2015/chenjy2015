package com.cjy.notebook.widget.dialog.helper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * 项目名称：CloudSurveyPro 类名称：SLDialogSelectListenner 类描述：单选或者多选回调 创建人：chenjiayou
 * 创建时间：2015-7-15 pm 9:52
 * 
 * @version
 * 
 */
public interface SLDialogSelectListenner {

	/**
	 * 单选值 选中
	 * @param index      	Adapter被选中的位置
	 * @param position   	被选中的值在数据源中的位置
	 * @param select	  	 被选中的值	
	 * @param object		自定义附加内容	不需要可以传null 自己判断
	 */
	public void onSingleSelect(int index, int position, String select, Object object);
	
	/**
	 * 多选值 选中
	 * @param index         Adapter被选中的位置
	 * @param selects		被选中的值在数据源中的位置 
	 * @param selectString  被选中的值
	 * @param object		自定义附加内容	不需要可以传null 自己判断
	 */
	public void onMultiSelect(int index, ArrayList<String> selects, HashMap<Integer,String> selectString, Object object);
	
	/**
	 * 输入框 填值完成
	 * @param index		          在数据源中的位置
	 * @param content		填充内容 可能为空
	 * @param object		自定义附加内容	不需要可以传null 自己判断
	 */
	public void onEditFinish(int index, String content, Object object);
	
	/**
	 * 普通对话框选择完毕
	 * @param tag	 		自定义标识 （当前Dialog的唯一标识）
	 * @param buttonType	标识按下左键还是右键(ok,no)
	 * @param object		自定义附加内容	不需要可以传null 自己判断
	 */
	public void onButtonClick(int tag, DialogButtonType dialogButtonType, Object object);
}
