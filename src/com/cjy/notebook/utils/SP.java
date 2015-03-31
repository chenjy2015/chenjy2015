package com.cjy.notebook.utils;

import com.cjy.notebook.config.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {

	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		return context.getSharedPreferences(Common.DEFAUT_CJY_STORAGEPATH,
				Context.MODE_PRIVATE);
	}

}
