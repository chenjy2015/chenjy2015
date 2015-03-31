package com.cjy.notebook.utils;

/**
 * @author：陈家有
 * @Time：2014-9-11
 * @Description：Dialog辅助类
 */
import com.cjy.notebook.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint({ "ValidFragment", "NewApi" })
public class DialogUtils extends DialogFragment {

	private String mTitle;
	private String mMessage;
	private String mPositive;
	private String mNegative;
	private String mPrompt;
	private int DialogNumber; // Dialog类型标识
	private Handler mHandler;
	private int PositiveButton = 0;
	private int NegativeButton = 1;

	/**
	 * 进度提示Dialog
	 * */
	@SuppressLint("ValidFragment")
	public DialogUtils(String prompt) {
		super();
		this.mPrompt = prompt;
		this.DialogNumber = 1;
	}

	/**
	 * 选择按钮Dialog
	 * 如果只想显示单个按钮 在不显示的按钮字符串传null即可
	 * @param title
	 * @param Message
	 * @param Positivie
	 *            按钮字符
	 * @param mNegative
	 *            按钮字符
	 * @param mHandler
	 *            用于回调的Handler
	 * @param PositiveButton
	 *            用于标识按钮返回的Handler的What值
	 * @param NegativeButton
	 *            用于标识按钮返回的Handler的What值
	 * */
	public DialogUtils(String mTitle, String mMessage, String mPositive,
			String mNegative, Handler mHandler, int PositiveButton,
			int NegativeButton) {
		super();
		this.mTitle = mTitle;
		this.mMessage = mMessage;
		this.mPositive = mPositive;
		this.mNegative = mNegative;
		this.mHandler = mHandler;
		this.PositiveButton = PositiveButton;
		this.NegativeButton = NegativeButton;
		this.DialogNumber = 2;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		switch (DialogNumber) {
		case 1:
			return ProgressDialog();
		case 2:
			return ChoiceDialog();
		}
		return ProgressDialog();
	}

	public CustomProgressDialog ProgressDialog() {
		CustomProgressDialog dialog = new CustomProgressDialog(getActivity(),
				R.style.CustomProgressDialog);
		return dialog;
	}

	public CustomDialog ChoiceDialog() {

		CustomDialog dialog = new CustomDialog(getActivity(),
				R.style.CustomDialog, true);
		return dialog;
	}

	/** 进度提示Dialog */
	class CustomProgressDialog extends Dialog {

		private ImageView iv;
		private TextView tv;

		public CustomProgressDialog(Context context, int theme) {
			super(context, theme);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);

			setContentView(R.layout.dialog_progress);

			tv = (TextView) findViewById(R.id.tv);
			tv.setText(mPrompt);
			iv = (ImageView) findViewById(R.id.iv);
			AnimationDrawable ad = (AnimationDrawable) iv.getBackground();
			ad.start();
		}

	}

	/** 选择提示Dialog 
	 * 通过设置Negative按钮显示或者隐藏来选择单选还是双选提示
	 * */
	public class CustomDialog extends Dialog implements
			android.view.View.OnClickListener {

		private TextView title;
		private TextView message;
		private Button positive;
		private Button negative;

		public CustomDialog(Context context, int theme, boolean isShow) {
			super(context, theme);
			setPBShow(isShow);
			setNBShow(isShow);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);

			setContentView(R.layout.dialog_custom);
			title = (TextView) findViewById(R.id.title);
			message = (TextView) findViewById(R.id.message);
			positive = (Button) findViewById(R.id.positiveButton);
			negative = (Button) findViewById(R.id.negativeButton);
			title.setText(mTitle);
			message.setText(mMessage);
			isCanAviable(mPositive,positive);
			isCanAviable(mNegative,negative);
//			positive.setOnClickListener(this);
//			negative.setOnClickListener(this);
			
			Window dialogWindow = getWindow();
			WindowManager.LayoutParams para = getWindow().getAttributes();
			para.x = 0; // 设置起始点
			para.y = 0;
			para.width = WindowManager.LayoutParams.MATCH_PARENT;
			// 增加Dialog弹出时动画
			//para.windowAnimations = R.style.dialogWindowAnim;
			para.gravity = Gravity.CENTER;
			 //实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0x0000ff00);
			dialogWindow.setBackgroundDrawable(dw);
		}

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.positiveButton:
				mHandler.sendEmptyMessage(PositiveButton);
				break;

			case R.id.negativeButton:
				mHandler.sendEmptyMessage(NegativeButton);
				break;
			}

		}
		
		//通过传值判断当前View是否可用
		public void isCanAviable(String text, TextView v){
			if(text == null || text.equals("")){
				v.setVisibility(View.GONE);
				return ;
			}
			v.setText(text);
			v.setOnClickListener(this);
		}

		public void setPBShow(boolean show) {
			if (!show) {
				positive.setVisibility(View.GONE);
			}
		}

		public void setNBShow(boolean show) {
			if (!show) {
				negative.setVisibility(View.GONE);
			}
		}

	}

}
