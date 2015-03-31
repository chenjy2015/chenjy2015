package com.cjy.notebook.adapter;

/**
 * @author chenjiayou
 * @feature Notes列表类
 * @createTime: 2014.11.4
 * @category: CJY Studio
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.object.Notes;
import com.cjy.notebook.viewholder.NotesViewHolder;

public class NotesAdapter extends BaseAdapter {

	private Context mContext;
	private List<Notes> data;
	
	public NotesAdapter(Context context, List<Notes> data){
		this.mContext = context;
		this.data = data;
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
		NotesViewHolder holder;
		if(convertView == null){
			holder = new NotesViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.adapter_notes, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.time_create = (TextView) convertView.findViewById(R.id.time_create);
			holder.time_change = (TextView) convertView.findViewById(R.id.time_change);
			convertView.setTag(holder);
		}
		holder = (NotesViewHolder) convertView.getTag();
		holder.title.setText(data.get(position).getTitle());
		holder.content.setText(data.get(position).getContent());
		holder.time_create.setText(data.get(position).getTime_create());
		holder.time_change.setText(data.get(position).getTime_change());
		return convertView;
	}
	
}
