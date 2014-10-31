package com.xun.iaskianswer.manager;

import android.content.Context;
import android.os.Bundle;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.config.Config;
import com.xun.iaskianswer.config.Constants;

/**
 * @author wangxun
 * 
 *         2014-10-31
 */
// 百度语音设置manager
public class VoiceRequestManager {
	private static VoiceRequestManager instance = null;

	private VoiceRequestManager() {

	}

	public static VoiceRequestManager getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new VoiceRequestManager();
		}
	}

	public void initVoiceParams(Context context, BaiduASRDigitalDialog mDialog,
			DialogRecognitionListener mRecognitionListener) {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		Bundle params = new Bundle();
		params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
		params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
				Constants.SECRET_KEY);
		params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
				Config.DIALOG_THEME);
		mDialog = new BaiduASRDigitalDialog(context, params);
		mDialog.setDialogRecognitionListener(mRecognitionListener);
		mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP,
				Config.CURRENT_PROP);
		mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
				Config.getCurrentLanguage());
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE,
				Config.PLAY_START_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE,
				Config.PLAY_END_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE,
				Config.DIALOG_TIPS_SOUND);
		mDialog.show();
	}

	public void showVoiceDialog(BaiduASRDigitalDialog mDialog) {
		if (mDialog != null) {
			mDialog.show();
		}
	}

}
