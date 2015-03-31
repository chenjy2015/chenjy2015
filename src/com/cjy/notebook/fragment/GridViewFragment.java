package com.cjy.notebook.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cjy.notebook.R;
import com.cjy.notebook.adapter.ImageGridAdapter;
import com.cjy.notebook.config.Config;
import com.cjy.notebook.object.FileVO;
import com.cjy.notebook.utils.DialogUtils;
import com.cjy.notebook.utils.FileUtils;

@SuppressLint("NewApi")
public class GridViewFragment extends Fragment {
	
	private GridView gv;
	private ImageGridAdapter adapter;
//	private List<HashMap<String, String>> data;
	private List<FileVO> data;
	private DialogUtils dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_gridview, container,
				false);
		gv = (GridView) view.findViewById(R.id.gv);
		show();
		getData();
		return view;
	}

	/**
	 * 网络图片
	 * */
	public void getData() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				data = new ArrayList<FileVO>();
//				for (int i = 0; i < Config.imageUrls.length; i++) {
//					FileVO fileVO = new FileVO();
//					fileVO.setName("Number"+(i+1)+"Picture");
//					fileVO.setPath(Config.imageUrls[i]);
//					data.add(fileVO);
//				}
				data = FileUtils.getFileList();
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void show() {
		dialog = new DialogUtils("正在加载数据.....");
		dialog.show(getActivity().getFragmentManager(), "ListViewFragment");
	};

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	};

	public Handler handler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			if(data != null && data.size() > 0){
				adapter = new ImageGridAdapter(getActivity(), data);
				gv.setAdapter(adapter);
			}
			dismiss();
		};

	};

	/**
	 * 本地图片
	 * */
	// public void getData(){
	// data = new ArrayList<HashMap<String,String>>();
	// File file = new File("/sdcard/xUtils");
	// File[] files;
	// if(file != null){
	// files = file.listFiles();
	// for(int i=0; i<files.length; i++){
	// HashMap<String,String> map = new HashMap<String, String>();
	// map.put("imageSrc",files[i].getAbsolutePath());
	// map.put("text", "Number"+(i+1)+"Picture");
	// data.add(map);
	// }
	// }
	//
	// }
	
}
