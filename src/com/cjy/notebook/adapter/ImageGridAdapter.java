package com.cjy.notebook.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.object.FileVO;
import com.cjy.notebook.utils.ImageLoaderUtils;
import com.cjy.notebook.widget.image.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageGridAdapter extends BaseAdapter {

	private Context mContext;
//	private List<HashMap<String, String>> data;
	private List<FileVO> data;
	private ImageLoaderUtils mImageloader;
	private ImageLoader imageLoader; //universal
	
	public ImageGridAdapter(Context context, List<FileVO> data) {
		this.mContext = context;
		this.data = data;
		mImageloader = ImageLoaderUtils.getInstance(mContext);
		imageLoader = ImageLoader.getInstance();
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_image_grid, null);
			viewHolder = new ViewHolder();
			viewHolder.iv = (RoundImageView) convertView.findViewById(R.id.iv);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String text = data.get(position).getName();
		String fileSrc = data.get(position).getPath();
		viewHolder.tv.setText(text);
		viewHolder.iv.setTag(fileSrc);
		mImageloader.ImageLoader(fileSrc, viewHolder.iv);
		//imageLoader.displayImage(fileSrc, viewHolder.iv);
		//imageLoader.displayImage(fileSrc, viewHolder.iv, CJYApplication.options);
		return convertView;
	}

	class ViewHolder {
		TextView tv;
		RoundImageView iv;
	}

}
