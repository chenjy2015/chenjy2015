package com.cjy.notebook;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cjy.notebook.config.Common;
import com.cjy.notebook.object.Notes;
import com.cjy.notebook.utils.DialogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

@SuppressLint("NewApi")
public class GsonActivity extends Activity implements OnClickListener {

	private Button leftBtn;
	private TextView title;
	private TextView title_gson, content_gson;
	private Button mCreateBtn, mReadBtn;
	private EditText mEdit;
	private View titleView;
	private Gson gson;
	private DialogUtils dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_gson);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		titleView = findViewById(R.id.title_layout);
		leftBtn = (Button) titleView.findViewById(R.id.left_bt);
		title = (TextView) titleView.findViewById(R.id.textTitle);
		title.setText("Json与对象的转换");
		title_gson = (TextView) findViewById(R.id.title_gson);
		content_gson = (TextView) findViewById(R.id.content_gson);
		mCreateBtn = (Button) findViewById(R.id.create_gson);
		mReadBtn = (Button) findViewById(R.id.read_gson);
		mEdit = (EditText) findViewById(R.id.edit_gson);

		leftBtn.setOnClickListener(this);
		mCreateBtn.setOnClickListener(this);
		mReadBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.left_bt:
			finish();
			break;
		case R.id.create_gson:
			title_gson.setText("create Json");
			if (checkEdit()) {
				createJson();
			} else {
				dialog = new DialogUtils("温馨提示", "无效字符无法生成Json", "确定", null,
						mHandler, Common.POSITIVEBUTTON, Common.NEGATIVEBUTTON);
				dialog.show(getFragmentManager(), "");
			}
			break;
		case R.id.read_gson:
			title_gson.setText("read Json for input");
			readJson();
			break;
		}
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	};

	public Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			dismiss();
		};
	};

	public boolean checkEdit() {
		if (mEdit.getText().toString().trim() != null
				&& !mEdit.getText().toString().trim().equals("")) {
			return true;
		}
		return false;
	}

	public List<Notes> createNotes(int size) {
		List<Notes> list = new ArrayList<Notes>();
		for (int i = 0; i < size; i++) {
			Notes note = new Notes();
			note.setTitle("noteTitle");
			note.setId(i + "");
			note.setContent("noteContent" + (i + 1));
			list.add(note);
		}
		return list;
	}

	public void createJson() {
		String JsonStr = "";
		gson = new Gson();
		int size = Integer.valueOf(mEdit.getText().toString().trim());// 获取输入框数据
		List<Notes> list = createNotes(size);
		Type type = new TypeToken<List<Notes>>() {
		}.getType();
		JsonStr = gson.toJson(list, type);
		content_gson.setText(JsonStr);
	}

	public void readJson() {
		String content = content_gson.getText().toString();
		if (content != null && !content.equals("")) {
			JsonReader reader = new JsonReader(new StringReader(content));
			try {
				reader.beginArray();
				while(reader.hasNext()){
					reader.beginObject();
					while(reader.hasNext()){
						String TagName = reader.nextName();
						Log.i("reader", "TagName----"+TagName);
						Log.i("reader", reader.nextString());
					}
					reader.endObject();
				}
				reader.endArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
