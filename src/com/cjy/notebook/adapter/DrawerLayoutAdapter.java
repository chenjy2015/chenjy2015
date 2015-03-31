package com.cjy.notebook.adapter;

/**
 * @author chenjiayou
 * @feature Notes列表类
 * @createTime: 2014.11.4
 * @category: CJY Studio
 */

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.viewholder.DrawerLayoutViewHolder;

@SuppressLint("NewApi")
public class DrawerLayoutAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> data;
	private PackageManager pm ;
	
	public DrawerLayoutAdapter(Context context, List<String> data){
		this.mContext = context;
		this.data = data;
		pm = context.getPackageManager();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		DrawerLayoutViewHolder holder;
		if(convertView == null){
			holder = new DrawerLayoutViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.adapter_drawerlayout, null);
			holder.DrawerText = (TextView) convertView.findViewById(R.id.page_name);
			holder.DrawerImage = (ImageView) convertView.findViewById(R.id.page_icon);
			convertView.setTag(holder);
		}
		holder = (DrawerLayoutViewHolder) convertView.getTag();
		holder.DrawerText.setText(data.get(position));
		return convertView;
	}
	
}
