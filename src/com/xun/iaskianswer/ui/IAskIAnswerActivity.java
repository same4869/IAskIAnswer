package com.xun.iaskianswer.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.config.Constants;
import com.xun.iaskianswer.entity.response.TextResponse;
import com.xun.iaskianswer.manager.BaiduLocationManager;
import com.xun.iaskianswer.manager.VoiceRequestManager;
import com.xun.iaskianswer.util.AnimUtil;
import com.xun.iaskianswer.util.HttpUtil;
import com.xun.iaskianswer.util.JSONUtil;
import com.xun.iaskianswer.util.PowerUtil;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class IAskIAnswerActivity extends Activity {
	private TextView tv;
	private Button btn;
	private TextView tv_tips;
	private TextView add;

	private Handler handler2 = new Handler();
	private RandomQueryThread randomQueryThread;

	private DialogRecognitionListener mRecognitionListener;
	private VoiceRequestManager voiceRequestManager;
	private BaiduLocationManager baiduLocationManager;
	private BaiduASRDigitalDialog mDialog = null;
	private String turingResult = null; // 图灵机器人返回回来的JSON
	private String SEARCH_TYPE = null; // 截取出来的返回类型码

	private Boolean is2CallBack = false;// 是否双击退出

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iask_ianswer);

		initView();
		initSpeechListener();
		initData();
	}

	private void initData() {
		voiceRequestManager = VoiceRequestManager.getInstance();
		baiduLocationManager = BaiduLocationManager.getInstance();
		baiduLocationManager.startLocationUpdate(add, getApplicationContext());
		if (randomQueryThread == null) {
			randomQueryThread = new RandomQueryThread();
			randomQueryThread.start();
		}
	}

	private void initSpeechListener() {
		mRecognitionListener = new DialogRecognitionListener() {

			@Override
			public void onResults(Bundle results) {
				final ArrayList<String> rs = results != null ? results
						.getStringArrayList(RESULTS_RECOGNITION) : null;
				if (rs != null && rs.size() > 0) {
					new Thread() {
						@Override
						public void run() {
							turingResult = HttpUtil.httpGetUtil(rs.get(0));
							hanlder.sendEmptyMessage(0);
							super.run();
						}
					}.start();
				}

			}
		};
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(null);
				voiceRequestManager.initVoiceParams(IAskIAnswerActivity.this,
						mDialog, mRecognitionListener);
				voiceRequestManager.showVoiceDialog(mDialog);
			}
		});

	}

	Handler hanlder = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			tv.setText(turingResult);
			// Toast.makeText(IAskIAnswerActivity.this,
			// turingResult.substring(8, 14), Toast.LENGTH_SHORT).show();
			SEARCH_TYPE = turingResult.substring(8, 14);
			int type = Integer.parseInt(SEARCH_TYPE);
			// 100000 文本类数据
			// 200000 网址类数据
			// 301000 小说
			// 302000 新闻
			// 304000 应用、软件、下载
			// 305000 列车
			// 306000 航班
			// 307000 团购
			// 308000 优惠
			// 309000 酒店
			// 310000 彩票
			// 311000 价格
			// 312000 餐厅
			switch (type) {
			case 100000:
				TextResponse textResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
				Toast.makeText(getApplicationContext(), textResponse.text, Toast.LENGTH_SHORT).show();
				break;
			case 200000:

				break;
			case 301000:

				break;
			case 302000:

				break;
			case 304000:

				break;
			case 305000:

				break;
			case 306000:

				break;
			case 307000:

				break;
			case 308000:

				break;
			case 309000:

				break;
			case 310000:

				break;
			case 311000:

				break;
			case 312000:

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initView() {
		tv = (TextView) findViewById(R.id.tv);
		btn = (Button) findViewById(R.id.btn);
		tv_tips = (TextView) findViewById(R.id.tv_tips);
		AnimUtil.createQueryAnimation(tv_tips, Constants.toastTips);
		add = (TextView) findViewById(R.id.add);
	}

	private class RandomQueryThread extends Thread {
		@Override
		public void run() {
			while (true) {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						AnimUtil.createQueryAnimation(tv_tips,
								Constants.toastTips);
					}
				});
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		super.onDestroy();
	}

	// 百度语音设别的回调函数，备用
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!is2CallBack) {
				is2CallBack = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						is2CallBack = false;
					}
				}, 2500);

			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		return true;
	}

	@Override
	protected void onResume() {
		PowerUtil.acquireWakeLock(getApplicationContext());
		super.onResume();
	}

	@Override
	protected void onPause() {
		PowerUtil.releaseWakeLock();
		super.onPause();
	}
}
