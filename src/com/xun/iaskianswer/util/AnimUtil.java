package com.xun.iaskianswer.util;

import java.util.Random;

import android.animation.ObjectAnimator;
import android.widget.TextView;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class AnimUtil {
	private static Random random = new Random();
	private static String lastRandomQuery = null;
	private static int i = 0;

	// 根据数组里的东东让提示文字随机淡入淡出显示
	public static void createQueryAnimation(TextView tv, String[] exampleQuerys) {
		if (exampleQuerys.length != 1) {
			while (exampleQuerys[i].equals(lastRandomQuery)) {
				i = random.nextInt(exampleQuerys.length);
			}
			lastRandomQuery = exampleQuerys[i];
		}
		tv.setText(exampleQuerys[i]);
		ObjectAnimator
				.ofFloat(tv, "alpha", 0f, 0.5f, 0.8f, 1f, 1f, 0.8f, 0.5f, 0f)
				.setDuration(4000).start();
	}
}
