package com.cjy.notebook.widget.dialog.helper;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjy.notebook.R;




/**
 * 
 * 项目名称：CloudSurveyPro 类名称：BlobDialogItemAdapter 类描述：查勘模板填值界面 二级,三级菜单
 * 单选，多选Dialog适配器 创建人：chenjiayou 创建时间：2015-7-18 pm 15:38
 * 
 * @version
 * 
 */
public class DialogItemChoiseAdapter extends BaseAdapter {

	private ArrayList<String> mData;
	private HashMap<Integer, String> mMultSelectString; // 多选结果集合
	private Context mContext;

	/**
	 * 构造方法
	 * @param mData               要选择的数据源
	 * @param mContext
	 * @param mMultSelectString   被选中的数据键 为选中数据在数据源中的索引值 值为选中数据
	 */
	public DialogItemChoiseAdapter(ArrayList<String> mData, Context mContext,
			HashMap<Integer, String> mMultSelectString) {
		super();
		this.mData = mData;
		this.mContext = mContext;
		this.mMultSelectString = mMultSelectString;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_blogdialoghelper, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.position = position;
		String content = mData.get(position);
		viewHolder.text.setText(content == null ? "" : content);

		boolean isSelect = mMultSelectString.get(position) == null ? false
				: true;
		if (isSelect) {
			viewHolder.icon
					.setBackgroundResource(R.drawable.dialog_img_pressed);
		} else {
			viewHolder.icon.setBackgroundResource(R.drawable.dialog_img_normal);
		}

		return convertView;
	}

	private class ViewHolder {
		int position; // 标识唯一
		TextView text;
		ImageView icon;
		View convertView;

		public ViewHolder(View view) {
			this.convertView = view;
			this.text = (TextView) convertView
					.findViewById(R.id.id_dialog_text);
			this.icon = (ImageView) convertView
					.findViewById(R.id.id_dialog_icon);
		}

	}
}
