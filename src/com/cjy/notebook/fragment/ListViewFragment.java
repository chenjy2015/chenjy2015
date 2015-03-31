package com.cjy.notebook.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cjy.notebook.R;
import com.cjy.notebook.adapter.ImageListAdapter;
import com.cjy.notebook.config.Config;
import com.cjy.notebook.object.FileVO;
import com.cjy.notebook.utils.DialogUtils;

@SuppressLint("NewApi")
public class ListViewFragment extends Fragment {

	private ListView lv;
	private ImageListAdapter adapter;
//	private List<HashMap<String, String>> data;
	private List<FileVO> data;
	private DialogUtils dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_listview, container,
				false);
		lv = (ListView) view.findViewById(R.id.lv);
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
				for (int i = 0; i < Config.imageUrls.length; i++) {
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put("imageSrc", Config.imageUrls[i]);
//					map.put("text", "Number" + (i + 1) + "Picture");
//					data.add(map);
					FileVO fileVO = new FileVO();
					fileVO.setName("Number"+(i+1)+"Picture");
					fileVO.setPath(Config.imageUrls[i]);
					data.add(fileVO);
				}
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
			adapter = new ImageListAdapter(getActivity(), data);
			lv.setAdapter(adapter);
			dismiss();
		};

	};

	/**
	 * 本地图片
	 * */
//	public void getData() {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				data = new ArrayList<HashMap<String, String>>();
//				String path = getSDPath();
//				File file = new File(path+"/DCIM/");
//				File[] files;
//				if (file != null) {
//					files = file.listFiles();
//					for (int i = 0; i < files.length; i++) {
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put("imageSrc", files[i].getAbsolutePath());
//						map.put("text", "Number" + (i + 1) + "Picture");
//						data.add(map);
//					}
//				}
//				handler.sendEmptyMessage(0);
//			}
//		}).start();
//	}
	
	
	/** 取SD卡路径 **/
	private String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return "";
		}
	}
}
