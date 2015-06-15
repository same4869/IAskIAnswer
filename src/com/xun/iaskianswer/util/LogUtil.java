package com.xun.iaskianswer.util;

import android.util.Log;

/**
 * @author xwang
 * 
 *         2014年10月24日
 */
public class LogUtil {
	public static final Boolean is_debug = true;

	public static void d(String tag, String msg) {
		if (is_debug) {
			Log.d(tag, msg);
		}
	}
}
