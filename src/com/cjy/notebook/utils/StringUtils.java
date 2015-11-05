package com.cjy.notebook.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 项目名称：DataCollection 类名称：StringUtils 类描述：字符串操作辅助类 创建人：chenjiayou
 * 创建时间：2015-10-24 pm 16:24
 * 
 * @version
 * 
 */
public class StringUtils {

	final private static String TAG = "StringUtils";

	public static String getBase64(String str) {
		byte[] buff;
		try {
			buff = android.util.Base64.encode(str.getBytes("UTF-8"),
					Base64.DEFAULT);
			str = new String(buff);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static boolean isNullOrEmpty(Object value) {
		return (value == null) || ("".equals(value)) || ("null".equals(value));
	}

	/**
	 * 对请求数据进行排序加密
	 * */
	public static String getRequestCode(String appid, String apppwd,
			String signname, String time, String functionname, String key) {
		String[] codeArray = new String[] { appid, apppwd, signname, time,
				functionname };
		if (codeArray != null) {
			for (int i = 0; i < codeArray.length; i++)
				Log.d(TAG, ":codeArray::" + codeArray[i]);
			Arrays.sort(codeArray);
		}
		String codestr = "";
		if (codeArray != null) {
			for (int i = 0; i < codeArray.length; i++) {
				codestr += codeArray[i];
			}
		}
		MD5 m = new MD5();
		String code = m.getMD5(codestr + key);
		return code;
	}

	/**
	 * 判断textview的内容是否为"null"字符串
	 * */

	public static boolean TextViewIsNull(TextView text) {
		boolean isNull = false;
		if (text.getText().toString().equals("null")) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 判断当前号码是否可以拨打
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		isValid = isPhoneNumber(phoneNumber);
		if (!isValid) {
			isValid = isPhoneNumberEmergencyPhone(phoneNumber);
			if (!isValid) {
				isValid = isOperatorNumber(phoneNumber);
			}
		}
		return isValid;
	}

	/**
	 * 验证号码 手机号 固话均可
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	// 判断是否是紧急求救电话号码
	public static boolean isPhoneNumberEmergencyPhone(String phoneNumber) {
		boolean isValid = false;
		if (phoneNumber == null || phoneNumber.equals("")) {
			return isValid;
		}

		String number = phoneNumber.toString().trim();

		if (number.equals("999") || number.equals("112")
				|| number.equals("110") || number.equals("120")
				|| number.equals("119") || number.equals("122")) {

			isValid = true;
			return isValid;
		}

		return isValid;
	}

	// 判断是否是三大电信运营商客服电话
	public static boolean isOperatorNumber(String phoneNumber) {
		boolean isValid = false;
		if (phoneNumber == null || phoneNumber.equals("")) {
			return isValid;
		}

		String number = phoneNumber.toString().trim();

		if (number.equals("10086") || number.equals("10010")
				|| number.equals("10000")) {
			isValid = true;
			return isValid;
		}
		return isValid;
	}

	/**
	 * 中文转码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlEncoder(String str) {
		if (isNullOrEmpty(str))
			return "";
		String ret = "";
		try {
			ret = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 中文解码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlDecoder(String str) {
		if (isNullOrEmpty(str))
			return "";
		String ret = "";
		try {
			ret = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static String list2Str(ArrayList<String> list) {
		String result = ""; // 's','s','s'
		StringBuffer sb = new StringBuffer();
		for (String s : list) {
			sb.append(",");
			sb.append(s);
		}
		if (sb != null) {
			result = sb.deleteCharAt(0).toString();
		}
		return result;
	}

	public static void showShortToast(Context ctx, String message) {
		Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
	}

	public static long convertString2long(String val) {
		long result = 0;
		if (isNullOrEmpty(val))
			return result;
		try {
			result = Long.parseLong(val);
		} catch (NumberFormatException e) {
		}

		return result;
	}

	/**
	 * 得到文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNa(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
	}

	/**
	 * 得到文件後綴名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileEx(String url) {
		return url.substring(url.lastIndexOf("."));
	}

	public static boolean removeFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			boolean bool = file.delete();
			return bool;
		}
		return false;
	}

	/**
	 * 截取字符串
	 * 
	 * @param arrStr
	 * @param mSplit
	 *            截取表示 ，注意 转义 \\
	 * @param i
	 * @return
	 */
	public static String getSplitStr(String arrStr, String mSplit, int i) {

		String result = "";
		if (arrStr != null && !"".equals(arrStr)) {
			String[] tempStr = arrStr.split(mSplit);
			if (tempStr != null && tempStr.length > i) {
				result = tempStr[i];
			}
		}
		return result;
	}

	/**
	 * 检查是否合法的电话号码
	 * 
	 * @param phoneNumber
	 * @return
	 */
	// public static boolean isPhoneNumberValid(String phoneNumber) {
	//
	// boolean isValid = false;
	// String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";
	// String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
	// CharSequence inputStr = phoneNumber;
	//
	// Pattern pattern = Pattern.compile(expression);
	//
	// Matcher matcher = pattern.matcher(inputStr);
	//
	// Pattern pattern2 = Pattern.compile(expression2);
	//
	// Matcher matcher2 = pattern2.matcher(inputStr);
	//
	// if (matcher.matches() || matcher2.matches()) {
	// isValid = true;
	// }
	//
	// return isValid;
	//
	// }

	/** String 转 Double */
	public static double convertString2Double(String val) {
		double result = 0;
		if (isNullOrEmpty(val))
			return result;
		try {
			result = Double.parseDouble(val);
		} catch (NumberFormatException e) {
		}

		return result;
	}

	/**
	 * 限定字符长度
	 * 
	 * @param content
	 *            源字符串
	 * @param checkLength
	 *            限定长度 如没有限定长度 传 0
	 * @return 返回 限定长度字符串 +" .... "
	 */
	public static String checkStringLength(String content, int checkLength) {
		String result = "";
		if (content == null || content.equals("null")) {
			return result;
		}

		if (content.length() > checkLength) {
			if (checkLength > 0) {
				result = content.substring(0, checkLength) + "...";
			} else {
				result = result + "...";
			}
		} else {
			result = content == null ? "" : content;
		}
		return result;
	}

	/** 正则表达式 判断是否纯数字 */
	public static int ContentType(String content) {
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(content);
		if (m.matches()) {
			return 0;
		} else {
			return 1;
		}
	}

	/** 字符串转Double类型 */
	public static double parseDouble(String content) {
		double d = 0.0;
		if (content == null || content.equals("")) {
			return d;
		}

		try {
			d = Double.parseDouble(content);
		} catch (Exception e) {
			Log.e(TAG, content + "parseDouble Exception");
		}
		return d;
	}

	/** 判断字符串是否可用 返回空字符串或字符串本身 */
	public static String checkStringIsNull(String content) {
		if (content == null || content.equals("") || content.equals("null")) {
			return "";
		} else {
			return content;
		}
	}
	
	
	/**判断字符串类型的整型值*/
	public static String checkIntIsNull(String content){
		if (content == null || content.equals("") || content.equals("null")) {
			return "0";
		} else {
			return content;
		}
	}
	
	/**判断字符串类型的双精度值*/
	public static String checkDoubleIsNull(String content){
		if (content == null || content.equals("") || content.equals("null")) {
			return "0.0";
		} else {
			return content;
		}
	}

	/** 获取文件名称并进行中文转码 utf-8 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		String fileName = urlEncoder(file.getName());
		return fileName;
	};

	public static String convertInteger2String(int val) {
		if (val == 0) {
			return "";
		}
		return String.valueOf(val);
	}

	public static String convertDouble2String(double val) {
		if (val == 0) {
			return "";
		}
		return String.valueOf(val);
	}

	public static int convertString2Integer(String val) {
		int result = 0;
		if (isNullOrEmpty(val))
			return result;
		try {
			result = Integer.parseInt(val);
		} catch (NumberFormatException e) {
		}

		return result;
	}

	// 实验代码 将内部保存到本地SDCard文本形式
	public static void wirteContentToSdCard(Context mContext, String content,
			String fileName) {
		// String path = Contants.get_BACKUP_PATH(mContext);
		// File file = new File(path);
		// if (!file.exists()) {
		// file.mkdirs();
		// }
		//
		// File f = new File(path + "/" + fileName + ".txt");
		// try {
		// FileOutputStream fos = new FileOutputStream(f);
		// fos.write(content.getBytes());
		// fos.flush();
		// fos.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	// 替换、过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		String filterStr = "";
		Matcher m = null;
		try {
			str = str.replaceAll("【", "[").replaceAll("】", "]")
					.replaceAll("！", "!");// 替换中文标号
			String regEx = "[『』]"; // 清除掉特殊字符
			Pattern p = Pattern.compile(regEx);
			m = p.matcher(str);
			filterStr = m.replaceAll("").trim();
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filterStr;
	}

	// 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，
	// 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 
	 * @Description: TODO(得到功能验证信息字典排序后的MD5加密内容)
	 * @Title: getFunVerifyCode
	 * @param @param mContext
	 * @param @param code
	 * @param @return
	 * @return String
	 * @throws
	 * @Author：gonghg @CreateDate：2014-3-25 下午5:39:33
	 * @ModifiedBy： @ModifiedDate: 2014-3-25 下午5:39:33
	 */
	public static String getCodeAppKeyMd5(Context mContext, String appKey,
			String code) {
		return new MD5().getMD5(code + appKey);
	}
}
