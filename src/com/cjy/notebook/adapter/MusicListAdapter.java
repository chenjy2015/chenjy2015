package com.cjy.notebook.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.object.MusicVO;

public class MusicListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<MusicVO> data;

	public MusicListAdapter(Context context, ArrayList<MusicVO> list) {
		this.mContext = context;
		this.data = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return data.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_music_list, null);
			mViewHolder.tv = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		mViewHolder.tv.setText(data.get(position).getFilename());
		return convertView;
	}

	private class ViewHolder {
		TextView tv;
	}
	
	

}
