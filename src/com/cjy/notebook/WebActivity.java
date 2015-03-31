package com.cjy.notebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cjy.notebook.config.Common;
import com.cjy.notebook.utils.DialogUtils;

@SuppressLint("NewApi")
@SuppressWarnings("unused")
public class WebActivity extends Activity implements OnClickListener {

	private WebView web;
	private Button leftBtn, rightBtn;
	private TextView titleText;
	private WebChrome chrome;
	private WebClient client;
	private DialogUtils dialog;
	private static final String[] urls = { "file:///android_asset/webJavascript.html" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_web);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		titleText = (TextView) findViewById(R.id.textTitle);
		leftBtn = (Button) findViewById(R.id.left_bt);
		leftBtn.setOnClickListener(this);
		rightBtn = (Button) findViewById(R.id.right_bt);
		rightBtn.setText("加载WEB");
		rightBtn.setTextSize(10);
		rightBtn.setOnClickListener(this);
		titleText.setText("WebViewActivity");

		chrome = new WebChrome();
		client = new WebClient();
		web = (WebView) findViewById(R.id.web);
		web.setWebChromeClient(chrome);
		web.setWebViewClient(client);
		web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 设置滚动条风格
		web.setHorizontalScrollBarEnabled(false); // 设置滚动条不接受点击事件
		web.setHorizontalScrollbarOverlay(true);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setSupportZoom(false);// 设置支持放大
		web.getSettings().setBuiltInZoomControls(false);// 设置支持缩放
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.left_bt:
			finish();
			break;

		case R.id.right_bt:
			String url = urls[0];
//			if(isAviable(url)){
				web.loadUrl(url);
//			}else{
//				dialog = new DialogUtils("温馨提示！", "地址不可用!", "确定",null, 
//						mHandler, Common.POSITIVEBUTTON, 0);
//				dialog.show(getFragmentManager(), "onJsAlert");
//			}
			
			break;
		}
	}

	public Handler mHandler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case Common.POSITIVEBUTTON:
				dismiss();
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
			dialog.show(getFragmentManager(), "onJsAlert");
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
			dialog.show(getFragmentManager(), "onJsAlert");

		}

	}
}
