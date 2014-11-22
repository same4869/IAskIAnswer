package com.xun.iaskianswer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.TestFragPagerAdapter;
import com.xun.iaskianswer.config.Constants;
import com.xun.iaskianswer.manager.BaiduLocationManager;
import com.xun.iaskianswer.manager.VoiceActionManager;
import com.xun.iaskianswer.manager.VoiceRequestManager;
import com.xun.iaskianswer.util.AnimUtil;
import com.xun.iaskianswer.util.HttpUtil;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class MainFragment extends Fragment {
	boolean visible = true;
	private TextView tv;
	private Button btn;
	private TextView tv_tips;
	private TextView add;

	private DialogRecognitionListener mRecognitionListener;
	private VoiceRequestManager voiceRequestManager;
	private BaiduLocationManager baiduLocationManager;
	private RandomQueryThread randomQueryThread;

	private Handler handler2 = new Handler();
	// private RandomQueryThread randomQueryThread;

	private VoiceActionManager voiceActionManager;

	private String turingResult = null; // 图灵机器人返回回来的JSON
	private BaiduASRDigitalDialog mDialog = null;

	private FragmentActivity context;
	private static final String TAG = "MainFragment";

	private List<View> mListViews;
	private TestFragPagerAdapter mMyPagerAdapter;

	private OnResultReciviedListener callback;

	public MainFragment(FragmentActivity context, List<View> listViews, TestFragPagerAdapter myPagerAdapter) {
		this.context = context;
		mListViews = listViews;
		mMyPagerAdapter = myPagerAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, null);
		initUI(rootView);
		initSpeechListener();
		initData();
		mMyPagerAdapter.notifyDataSetChanged();
		return rootView;
	}

	private void initData() {
		voiceRequestManager = VoiceRequestManager.getInstance();
		baiduLocationManager = BaiduLocationManager.getInstance();
		voiceActionManager = VoiceActionManager.getInstance();
		baiduLocationManager.startLocationUpdate(add, context.getApplicationContext());
		if (randomQueryThread == null) {
			randomQueryThread = new RandomQueryThread();
			randomQueryThread.start();
		}
	}

	private void initUI(View root) {

		tv = (TextView) root.findViewById(R.id.tv);
		btn = (Button) root.findViewById(R.id.btn);
		tv_tips = (TextView) root.findViewById(R.id.tv_tips);
		AnimUtil.createQueryAnimation(tv_tips, Constants.toastTips);
		add = (TextView) root.findViewById(R.id.add);

	}

	private void initSpeechListener() {
		mRecognitionListener = new DialogRecognitionListener() {

			@Override
			public void onResults(Bundle results) {
				final ArrayList<String> rs = results != null ? results.getStringArrayList(RESULTS_RECOGNITION) : null;
				if (rs != null && rs.size() > 0) {
					new Thread() {
						@Override
						public void run() {
							Boolean isVoiceAction = voiceActionManager.isVoiceAction(context.getApplicationContext(),
									rs.get(0));
							if (isVoiceAction == true) {

							} else {
								turingResult = HttpUtil.httpGetUtil(rs.get(0));
								hanlder.sendEmptyMessage(0);
							}
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
				voiceRequestManager.initVoiceParams(context, mDialog, mRecognitionListener);
				voiceRequestManager.showVoiceDialog(mDialog);
			}
		});
	}

	// 图灵机器人oneBox分支Handler
	Handler hanlder = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			tv.setText(turingResult);
			// 回调到activity中去，实现fragment的通信
			callback.onResult(turingResult);
			super.handleMessage(msg);
		}

	};

	private class RandomQueryThread extends Thread {
		@Override
		public void run() {
			while (true) {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						AnimUtil.createQueryAnimation(tv_tips, Constants.toastTips);
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
	public void onDestroy() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		mMyPagerAdapter.notifyDataSetChanged();
		super.onDestroy();
	}

	public interface OnResultReciviedListener {
		public void onResult(String turingResult);
	}
}
