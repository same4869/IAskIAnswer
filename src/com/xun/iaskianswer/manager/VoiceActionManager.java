package com.xun.iaskianswer.manager;

import android.content.Context;

import com.xun.iaskianswer.R;

/**
 * @author wangxun
 * 
 *         2014-10-31
 */
// VoiceAction处理manager，有特殊的VoiceAction在这里处理，就不走图灵机器人
public class VoiceActionManager {
	private static VoiceActionManager instance = null;

	private VoiceActionManager() {

	}

	public static VoiceActionManager getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new VoiceActionManager();
		}
	}

	public Boolean isVoiceAction(Context context, String string) {
		String[] voiceActionArray = context.getResources().getStringArray(
				R.array.voice_action);
		if (voiceActionArray != null) {
			for (int i = 0; i < voiceActionArray.length; i++) {
				if (string.equals(voiceActionArray[i])) {
					return true;
				}
			}
		}
		return false;
	}
}
