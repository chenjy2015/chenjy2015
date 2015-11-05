package com.cjy.notebook.widget.dialog.helper;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjy.notebook.R;
import com.cjy.notebook.utils.StringUtils;
import com.cjy.notebook.widget.dialog.factory.NiftyDialogBuilder;

/**
 * 
 * 项目名称：CloudSurveyPro 类名称：BlobDialogHelper 类描述：查勘模板填值界面 二级,三级菜单辅助对话框
 * 创建人：chenjiayou 创建时间：2015-7-15 pm 11:58
 * 
 * 注意：在调用SLDialogSelectListenner 这个接口回调的时候 不要直接在回调方法直接显示一个Dialog
 * 因为此方法回调还未出刚刚的Dialog对象 所以如果需要在Dialog关闭后 马上又起一个Dialog 需要用到Handler来转一下
 * 也就是在SLDialogSelectListenner回调方法里面 将结果通过handler发送在 handler里面新启一个Dialog实例
 * 
 * @version
 * 
 */
@SuppressLint("UseSparseArrays")
public class DialogHelper {

	private static NiftyDialogBuilder mDialogBuilder;
	// private static Context mContext;
	private static DialogHelper mInstance;
	private static ShowType mShowType;
	private static View parentView;
	private static ListView mSListView; // 单选/多选展示ListView
	private static EditText mEditText; // 输入框
	private static LinearLayout mProgressLayout; // 进度条父元素
	private static TextView mProgressText;// 进度加载提示内容
	private static LoadingView mProgressImg; // 进度展示图片
	private static RotateAnimation mRotateAnimatin;
	private static DialogItemChoiseAdapter mMultAdapter; // 单/多选适配器
	private static ArrayList<String> mMultSelectPosition;// 多选位置集合
	private static HashMap<Integer, String> mMultSelectString; // 多选结果集合

	/**
	 * 内部类 枚举 标识当前展示的输入类型
	 */
	public enum ShowType {

		showSingle(0), // 单选
		showMulit(1), // 多选
		showInput(2), // 输入框
		showNormal(3), // 普通对话提示
		showProgress(4), // 进度提示框
		showDismiss(5); // 标识对话框已关闭 还原所有View
		int type;

		private ShowType(int type) {
			// TODO Auto-generated constructor stub
			this.type = type;
		}
	}

	// public static DialogHelper getInstance(Context context) {
	// if (mInstance == null) {
	// mInstance = new DialogHelper();
	// }
	// mContext = context;
	// init();
	// return mInstance;
	// }

	private static void initView(Context mContext) {
		parentView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_blob_dialoghelper, null);
		mSListView = (ListView) parentView.findViewById(R.id.id_single);
		mEditText = (EditText) parentView.findViewById(R.id.id_edit);
		mProgressLayout = (LinearLayout) parentView
				.findViewById(R.id.id_progress_layout);
		mProgressImg = (LoadingView) parentView
				.findViewById(R.id.id_progress_img);
		mProgressImg.initAmin();
		mProgressText = (TextView) parentView
				.findViewById(R.id.id_progress_text);

	}

	/**
	 * 刷新UI 当前Dialog中的单选列表或输入框是否显示
	 * 
	 * @param showType
	 *            对话框类型 单选，多选，输入框，进度提示，单个按钮提示等
	 * @param isShow
	 *            显示或隐藏
	 * @param inputType
	 *            输入法类型 默认类型InputType.TYPE_NULL
	 */
	private static void updateView(ShowType showType, boolean isShow,
			int inputType, int len) {
		mShowType = showType;// 记录当前Dialog类型
		switch (showType) {
		// 单选/多选 ListView
		case showSingle:
		case showMulit:
			if (isShow) {
				mSListView.setVisibility(View.VISIBLE);
				mEditText.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.GONE);
			} else {
				mSListView.setVisibility(View.GONE);
			}
			break;
		// 输入框 EditText
		case showInput:
			if (isShow) {

				mEditText
						.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
								len == 0 ? 30 : len) });
				mEditText.setVisibility(View.VISIBLE);
				if (inputType != 0) {
					mEditText.setInputType(inputType);
				}
				mSListView.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.GONE);
			} else {
				mEditText.setVisibility(View.GONE);
			}
			break;
		// 普通提示框
		case showNormal:
			if (isShow) {
				mEditText.setVisibility(View.GONE);
				mSListView.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.GONE);
			}
			break;
		// 进度提示框
		case showProgress:
			if (isShow) {
				mEditText.setVisibility(View.GONE);
				mSListView.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.VISIBLE);
				mProgressText.setVisibility(View.VISIBLE);
				mProgressImg.setVisibility(View.VISIBLE);
				if (mRotateAnimatin == null) {
					mRotateAnimatin = new RotateAnimation(0f, 359f,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
				}
				LinearInterpolator lin = new LinearInterpolator();
				mRotateAnimatin.setInterpolator(lin);
				mRotateAnimatin.setRepeatCount(-1);
				mProgressImg.startAnimation();
			} else {
				mProgressImg.EndAnimation();
				mProgressText.setText("");
				mProgressText.setVisibility(View.GONE);
				mProgressImg.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.GONE);
			}
			break;

		// 对话框关闭 还原所有View
		case showDismiss:
			// view在生成对话框对象时必然会生成所以这里只需要判断对象是否存在就行
			if (mDialogBuilder != null) {
				mEditText.setText("");
				mEditText.setVisibility(View.GONE);
				mSListView.setVisibility(View.GONE);
				mProgressLayout.setVisibility(View.GONE);
				mProgressImg.clearAnimation();
				mProgressText.setText("");
				if (mRotateAnimatin != null) {
					mRotateAnimatin.cancel();
					mRotateAnimatin = null;
				}
			}
			break;
		}
	}

	private static void initDialog(Context mContext) {
		dismiss();
		initView(mContext);
		mDialogBuilder = new NiftyDialogBuilder(mContext).withDuration(50)
				.isCancelableOnTouchOutside(false);

	}

	public static void dismiss() {
		if (mDialogBuilder != null && mDialogBuilder.isShowing()) {
			mDialogBuilder.dismiss();
			mDialogBuilder.cancel();
			mDialogBuilder = null;
		}
	}

	public static ShowType getShowType() {
		return mShowType;
	}

	public static void setDialogCancelable(boolean isCancelable) {
		if (mDialogBuilder != null) {
			mDialogBuilder.setCancelable(isCancelable);
		}
	}

	public static void setDialogCanceledOnTouchOutside(boolean isCancelable) {
		if (mDialogBuilder != null) {
			mDialogBuilder.setCanceledOnTouchOutside(isCancelable);
		}
	}

	/**
	 * 单选框
	 * 
	 * @param index
	 *            当前被选中的Item在Adapter中的位置
	 * @param list
	 *            数据源
	 * @param selectListenner
	 *            选中回调
	 * @param object
	 *            自定义附加内容 不需要可以传null 自己判断
	 */
	public static void showSingleDialog(Context mContext, final int index,
			final ArrayList<String> list,
			final SLDialogSelectListenner selectListenner, final Object object) {
		initDialog(mContext);
		mMultSelectPosition = new ArrayList<String>();
		mMultSelectString = new HashMap<Integer, String>();
		mMultAdapter = new DialogItemChoiseAdapter(list, mContext,
				mMultSelectString);

		// 加载适配器
		mSListView.setAdapter(mMultAdapter);
		mSListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 刷新UI
				mMultAdapter.getView(position, view, parent).invalidate();
				selectListenner.onSingleSelect(index, position,
						list.get(position), object);
				updateView(ShowType.showSingle, false, 0, 0);
			}
		});

		updateView(ShowType.showSingle, true, 0, 0);
		mDialogBuilder.setCustomView(parentView, mContext).show();
	}

	/**
	 * 复选框
	 * 
	 * @param index
	 *            当前被选中的Item在Adapter中的位置
	 * @param list
	 *            数据源
	 * @param selectListenner
	 *            选中回调
	 * @param object
	 *            自定义附加内容 不需要可以传null 自己判断
	 */
	public static void showMulitDialog(Context mContext, final int index,
			final ArrayList<String> list,
			final SLDialogSelectListenner selectListenner, final Object object) {
		initDialog(mContext);
		mMultSelectPosition = new ArrayList<String>();
		mMultSelectString = new HashMap<Integer, String>();

		mMultAdapter = new DialogItemChoiseAdapter(list, mContext,
				mMultSelectString);
		// 加载适配器
		mSListView.setAdapter(mMultAdapter);
		mSListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String key = position + "";
				if (mMultSelectPosition.contains(key)) {
					mMultSelectPosition.remove(key);
					mMultSelectString.remove(position);
				} else {
					mMultSelectPosition.add(key);
					mMultSelectString.put(position, list.get(position));
				}
				// 刷新UI
				mMultAdapter.getView(position, view, parent).invalidate();
			}
		});

		updateView(ShowType.showMulit, true, 0, 0);
		mDialogBuilder.setCustomView(parentView, mContext)
				.withButton1Text("选定").withButton2Text("取消")
				.setButton1Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						selectListenner.onMultiSelect(index,
								mMultSelectPosition, mMultSelectString, object);
						updateView(ShowType.showMulit, false, 0, 0);
					}
				}).setButton2Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						selectListenner
								.onMultiSelect(index, null, null, object);
						updateView(ShowType.showMulit, false, 0, 0);
					}
				}).show();

	}

	/**
	 * 输入框
	 * 
	 * @param index
	 *            当前被选中的Item在Adapter中的位置
	 * @param selectListenner
	 *            按钮点击回调
	 * @param title
	 * @param InputType
	 *            输入法类型
	 * @param object
	 *            自定义附加内容 不需要可以传null 自己判断
	 */
	public static void showEditDialog(Context mContext, final int index,
			final SLDialogSelectListenner selectListenner, String title,
			int InputType, final Object object, final int len) {
		initDialog(mContext);
		updateView(ShowType.showInput, true, InputType, len);
		mDialogBuilder.setCustomView(parentView, mContext).withTitle(title)
				.withButton1Text("选定").withButton2Text("取消")
				.setButton1Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String content = mEditText.getText().toString().trim();
						selectListenner.onEditFinish(index, content, object);
						updateView(ShowType.showInput, false, 0, len);
					}
				}).setButton2Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						selectListenner.onEditFinish(index, "", object);
						updateView(ShowType.showInput, false, 0, len);
					}
				}).show();

		// 解决Dialog编辑框输入法无法弹出问题 下面两行代码加入后即可弹出输入法
		mDialogBuilder.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		ShowKeyboard(mEditText);
	}

	/**
	 * 普通对话框
	 * 
	 * @param title
	 *            标题
	 * @param message
	 *            内容
	 * @param button1
	 *            坐标按钮文字
	 * @param button2
	 *            右边按钮文字
	 * @param tag
	 *            自定义唯一标识
	 * @param selectListenner
	 *            回调接口
	 * @param object
	 *            自定义附加内容 不需要可以传null 自己判断
	 */
	public static void showNormalDialog(Context mContext, String title,
			String message, String button1, String button2, final int tag,
			final SLDialogSelectListenner selectListenner, final Object object) {
		initDialog(mContext);
		updateView(ShowType.showNormal, true, 0, 0);
		mDialogBuilder.withTitle(title).withMessage(message)
				.withButton1Text(button1).withButton2Text(button2)
				.setButton1Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						updateView(ShowType.showNormal, false, 0, 0);
						selectListenner.onButtonClick(tag, DialogButtonType.OK,
								object);
					}
				}).setButton2Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						updateView(ShowType.showNormal, false, 0, 0);
						selectListenner.onButtonClick(tag, DialogButtonType.NO,
								object);

					}
				}).show();
	};

	/**
	 * 提示Dialog 单个按钮
	 * 
	 * @param title
	 * @param message
	 * @param button1
	 * @param tag
	 * @param selectListenner
	 * @param object
	 *            自定义附加内容 不需要可以传null 自己判断
	 */
	public static void showHintDialog(Context mContext, String title,
			String message, String button1, final int tag,
			final SLDialogSelectListenner selectListenner, final Object object) {
		initDialog(mContext);
		updateView(ShowType.showNormal, true, 0, 0);
		mDialogBuilder.withTitle(title).withMessage(message)
				.withButton1Text(button1)
				.setButton1Click(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						updateView(ShowType.showNormal, false, 0, 0);
						selectListenner.onButtonClick(tag, DialogButtonType.OK,
								object);
					}
				}).show();
	};

	/**
	 * 进度提示对话框
	 * 
	 * @param title
	 * @param progressText
	 *            加载时提示信息
	 */
	public static void showProgressDialog(Context mContext, String title,
			String progressText) {
		initDialog(mContext);
		mDialogBuilder.setCancelable(false);
		mDialogBuilder.setCanceledOnTouchOutside(false);
		updateView(ShowType.showProgress, true, 0, 0);
		mProgressText.setText(StringUtils.checkStringIsNull(progressText));
		mDialogBuilder.setCustomView(parentView, mContext).withTitle(title)
				.show();
	}

	/**
	 * 更新进度显示
	 * 
	 * @param progressText
	 */
	public static void updateProgressDialog(String progressText) {
		if (mProgressText != null) {
			mProgressText.setText(StringUtils.checkStringIsNull(progressText));
		}
	}

	// 隐藏虚拟键盘
	public static void HideKeyboard(EditText edit) {
		InputMethodManager imm = (InputMethodManager) edit.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(edit.getApplicationWindowToken(), 0);

		}
	}

	// 显示虚拟键盘
	public static void ShowKeyboard(View edit) {
		InputMethodManager imm = (InputMethodManager) edit.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);

	}
}
