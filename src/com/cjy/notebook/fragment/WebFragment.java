package com.cjy.notebook.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.config.Common;
import com.cjy.notebook.utils.DialogUtils;

@SuppressLint("NewApi")
public class WebFragment extends Fragment {

	private WebView web;
	private Button leftBtn, rightBtn;
	private TextView titleText;
	private WebChrome chrome;
	private WebClient client;
	private DialogUtils dialog;
	private static final String[] urls = { "file:///android_asset/webJavascript.html" };

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_web, container, false);
		chrome = new WebChrome();
		client = new WebClient();
		web = (WebView)view.findViewById(R.id.web);
		web.setWebChromeClient(chrome);
		web.setWebViewClient(client);
		web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 设置滚动条风格
		web.setHorizontalScrollBarEnabled(false); // 设置滚动条不接受点击事件
		web.setHorizontalScrollbarOverlay(true);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(false);// 设置支持放大
		web.getSettings().setBuiltInZoomControls(false);// 设置支持缩放
		return view;
	}
	
	/**
	 * 判断Url链接是否可用(泛指网址 非本地加载)
	 * */
	public boolean isAviable(String url) {
		if(!URLUtil.isNetworkUrl(url)){
			
			return false;
		}
		return true;
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void load(){
		String url = urls[0];
//		if(isAviable(url)){
			web.loadUrl(url);
//		}else{
//			dialog = new DialogUtils("温馨提示！", "地址不可用!", "确定",null, 
//					mHandler, Common.POSITIVEBUTTON, 0);
//			dialog.show(getFragmentManager(), "onJsAlert");
//		}
	}

	public Handler mHandler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case Common.POSITIVEBUTTON:
				dismiss();
				break;
				
			case Common.REFRESH:
				load();
				break;
			default:
				break;
			}
			
		};
	};

	@SuppressLint("NewApi")
	class WebChrome extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			// TODO Auto-generated method stub
			dialog = new DialogUtils("温馨提示！", "Web弹出一个Alert对话框!", "确定",null, 
					mHandler, Common.POSITIVEBUTTON, 0);
			dialog.show(getActivity().getFragmentManager(), "onJsAlert");
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, newProgress);
		}

	}

	class WebClient extends WebViewClient {

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);// 设置链接在本页面打开
			return true;
		}

		// 加载出错时响应
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			dialog = new DialogUtils("温馨提示！", errorCode+description+failingUrl, "确定", null,
					mHandler, Common.POSITIVEBUTTON, 0);
			dialog.show(getActivity().getFragmentManager(), "onJsAlert");

		}

	}
}
