package com.xun.iaskianswer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

public class IAskIAnswerActivity extends Activity {
	private TextView tv;
	private Button btn;

	private DialogRecognitionListener mRecognitionListener;
	private BaiduASRDigitalDialog mDialog = null;
	private int mCurrentTheme = Config.DIALOG_THEME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iask_ianswer);

		initView();
		initSpeechListener();
	}

	private void initSpeechListener() {
		mRecognitionListener = new DialogRecognitionListener() {

			@Override
			public void onResults(Bundle results) {
				ArrayList<String> rs = results != null ? results
						.getStringArrayList(RESULTS_RECOGNITION) : null;
				if (rs != null && rs.size() > 0) {
					tv.setText(rs.get(0));
				}

			}
		};
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(null);
				// if (mDialog == null || mCurrentTheme != Config.DIALOG_THEME)
				// {
				mCurrentTheme = Config.DIALOG_THEME;
				if (mDialog != null) {
					mDialog.dismiss();
				}
				Bundle params = new Bundle();
				params.putString(BaiduASRDigitalDialog.PARAM_API_KEY,
						Constants.API_KEY);
				params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
						Constants.SECRET_KEY);
				params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
						Config.DIALOG_THEME);
				mDialog = new BaiduASRDigitalDialog(IAskIAnswerActivity.this,
						params);
				mDialog.setDialogRecognitionListener(mRecognitionListener);
				// }
				mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP,
						Config.CURRENT_PROP);
				mDialog.getParams().putString(
						BaiduASRDigitalDialog.PARAM_LANGUAGE,
						Config.getCurrentLanguage());
				Log.e("DEBUG", "Config.PLAY_START_SOUND = "
						+ Config.PLAY_START_SOUND);
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
		});

	}

	private void initView() {
		tv = (TextView) findViewById(R.id.tv);
		btn = (Button) findViewById(R.id.btn);
	}

	@Override
	protected void onDestroy() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		super.onDestroy();
	}

	class MyVoiceRecogListener implements VoiceClientStatusChangeListener {

		@Override
		public void onClientStatusChange(int arg0, Object arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNetworkStatusChange(int arg0, Object arg1) {
			// TODO Auto-generated method stub

		}

	}

}
