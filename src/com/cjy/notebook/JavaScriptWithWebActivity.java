package com.cjy.notebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled", "NewApi" })
public class JavaScriptWithWebActivity extends Activity implements
		OnClickListener {

	TextView tv;
	Button btn1, btn2;
	WebView mWeb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scriptweb_layout);
		tv = (TextView) findViewById(R.id.text);
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		mWeb = (WebView) findViewById(R.id.webview);
		WebSettings mWebSetting = mWeb.getSettings();
		mWebSetting.setJavaScriptEnabled(true);
		mWebSetting.setDefaultTextEncodingName("utf-8");
		mWeb.addJavascriptInterface(new JavaScriptObjectInterface(), "jsObj");
		mWeb.loadUrl("file:///android_asset/JsAndWebViewTestDemo.html");
		mWeb.evaluateJavascript("(function(){return \'this\';}) ();", new ValueCallback<String>() {
			
			@Override
			public void onReceiveValue(String value) {
				// TODO Auto-generated method stub
				Log.e("JavaScriptWithWebActivity", value);
			}
		});
		
		SparseArray<String> sa = new SparseArray<String>();
		sa.put(0, "0");
		ArrayMap<String, Object> amap = new ArrayMap<String, Object>();
		amap.put("", "");
		SparseIntArray sia = new SparseIntArray();
		sia.append(0, 0);
		
	}

	class JavaScriptObjectInterface {

		// html 无参数调用
		public String htmlcalljava1() {
			return "this message come from java!";
		}

		// html 有参数调用 注意这里其实是在子线程中所以不可以直接给View赋值
		public void htmlcalljava2(String param) {
			handler.sendMessage(Message.obtain(handler, 0, param));
		}

		// html 调用此方法 实际就是调用自己
		public void javacallhtml() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mWeb.loadUrl("javascript: javacallhtml1()");
				}
			}).start();
		}

		// html调用此方法 实际就是调用自己
		public void javacallhtml2() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mWeb.loadUrl("javascript: javacallhtml2('message is come from java2')");
				}
			}).start();
		}
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String text = (String) msg.obj;
			tv.setText(text);
		};
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn1:// 调用html中的方法
			mWeb.loadUrl("javascript: javacallhtmlbybutton1()");
			break;

		case R.id.btn2:// 调用html中的方法
			mWeb.loadUrl("javascript: javacallhtmlbybutton2('java call html by button2')");
			break;
		}
	}
}
