package com.xun.iaskianswer.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.chiemy.jellyviewpager.widget.JellyViewPager;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.MyPagerAdapter;
import com.xun.iaskianswer.config.AnswerType;
import com.xun.iaskianswer.config.Constants;
import com.xun.iaskianswer.entity.response.AbstractResponse;
import com.xun.iaskianswer.entity.response.AppResponse;
import com.xun.iaskianswer.entity.response.FlightResponse;
import com.xun.iaskianswer.entity.response.FoodResponse;
import com.xun.iaskianswer.entity.response.GroupResponse;
import com.xun.iaskianswer.entity.response.HotelResponse;
import com.xun.iaskianswer.entity.response.LotteryResponse;
import com.xun.iaskianswer.entity.response.MovieResponse;
import com.xun.iaskianswer.entity.response.NewsResponse;
import com.xun.iaskianswer.entity.response.NovelResponse;
import com.xun.iaskianswer.entity.response.PriceResponse;
import com.xun.iaskianswer.entity.response.TextResponse;
import com.xun.iaskianswer.entity.response.TrainResponse;
import com.xun.iaskianswer.entity.response.UrlResponse;
import com.xun.iaskianswer.manager.BaiduLocationManager;
import com.xun.iaskianswer.manager.ResponseManager;
import com.xun.iaskianswer.manager.VoiceActionManager;
import com.xun.iaskianswer.manager.VoiceRequestManager;
import com.xun.iaskianswer.util.AnimUtil;
import com.xun.iaskianswer.util.HttpUtil;
import com.xun.iaskianswer.util.LogUtil;
import com.xun.iaskianswer.util.PowerUtil;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class IAskIAnswerActivity extends FragmentActivity {
	private TextView tv;
	private Button btn;
	private TextView tv_tips;
	private TextView add;

	private Handler handler2 = new Handler();
	private RandomQueryThread randomQueryThread;

	private DialogRecognitionListener mRecognitionListener;
	private VoiceRequestManager voiceRequestManager;
	private BaiduLocationManager baiduLocationManager;
	private ResponseManager responseManager;
	private VoiceActionManager voiceActionManager;
	private BaiduASRDigitalDialog mDialog = null;
	private String turingResult = null; // 图灵机器人返回回来的JSON
	private String SEARCH_TYPE = null; // 截取出来的返回类型码

	private Boolean is2CallBack = false;// 是否双击退出
	private static final String TAG = "IAskIAnswerActivity";

	private JellyViewPager myViewPager; // 页卡内容
	private List<View> list; // 存放页卡
	private LayoutInflater inflater;
	private MyPagerAdapter myPagerAdapter;
	private TextView textView1, textView2, textView3, textView4;
	private NetworkImageView networkImageView;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_main);

		initView();
		initSpeechListener();
		initData();
	}

	private void initData() {
		voiceRequestManager = VoiceRequestManager.getInstance();
		baiduLocationManager = BaiduLocationManager.getInstance();
		voiceActionManager = VoiceActionManager.getInstance();
		responseManager = ResponseManager.getInstance();
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
							Boolean isVoiceAction = voiceActionManager
									.isVoiceAction(getApplicationContext(),
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
				voiceRequestManager.initVoiceParams(IAskIAnswerActivity.this,
						mDialog, mRecognitionListener);
				voiceRequestManager.showVoiceDialog(mDialog);
			}
		});

	}

	// 图灵机器人oneBox分支Handler
	Handler hanlder = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			tv.setText(turingResult);
			// Toast.makeText(IAskIAnswerActivity.this,
			// turingResult.substring(8, 14), Toast.LENGTH_SHORT).show();
			// 返回码以4开头的只有5位，其他的为6位，这里要判断一下
			if (turingResult.charAt(8) != '4') {
				SEARCH_TYPE = turingResult.substring(8, 14);
			} else {
				SEARCH_TYPE = turingResult.substring(8, 13);
			}
			if (LogUtil.is_debug) {
				Log.d(TAG,
						"SEARCH_TYPE --> " + SEARCH_TYPE
								+ " turingResult.charAt(8) --> "
								+ turingResult.charAt(8));
			}
			int type = Integer.parseInt(SEARCH_TYPE);
			AnswerType type2 = AnswerType.fromInt(type);
			AbstractResponse mResponse = responseManager.productResponse(
					turingResult, type2);
			if (mResponse instanceof TextResponse) {
				TextResponse mTextResponse = (TextResponse) mResponse;
				tv_tips.setText(mTextResponse.text);
			} else if (mResponse instanceof UrlResponse) {
				UrlResponse mUrlResponse = (UrlResponse) mResponse;
				Toast.makeText(getApplicationContext(), mUrlResponse.url,
						Toast.LENGTH_SHORT).show();
			} else if (mResponse instanceof NovelResponse) {

			} else if (mResponse instanceof NewsResponse) {

			} else if (mResponse instanceof AppResponse) {

			} else if (mResponse instanceof TrainResponse) {

			} else if (mResponse instanceof FlightResponse) {
				FlightResponse mFlightResponse = (FlightResponse) mResponse;
				responseManager.notifyViewPagerDataChanged(mFlightResponse,
						IAskIAnswerActivity.this, list, textView1, textView2,
						textView3, textView4, myPagerAdapter, null, null);

			} else if (mResponse instanceof GroupResponse) {

			} else if (mResponse instanceof MovieResponse) {
				MovieResponse mMovieResponse = (MovieResponse) mResponse;
				responseManager
						.notifyViewPagerDataChanged(mMovieResponse,
								IAskIAnswerActivity.this, list, textView1,
								textView2, null, null, myPagerAdapter,
								networkImageView, imageView);

			} else if (mResponse instanceof HotelResponse) {

			} else if (mResponse instanceof LotteryResponse) {

			} else if (mResponse instanceof PriceResponse) {

			} else if (mResponse instanceof FoodResponse) {

			} else {

			}
			super.handleMessage(msg);
		}

	};

	private void initView() {
		inflater = getLayoutInflater();
		myViewPager = (JellyViewPager) findViewById(R.id.viewPager);
		list = new ArrayList<View>();
		View main_view = inflater.inflate(R.layout.activity_iask_ianswer, null);
		list.add(main_view);
		myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), list);
		myViewPager.setAdapter(myPagerAdapter);
		myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

		tv = (TextView) main_view.findViewById(R.id.tv);
		btn = (Button) main_view.findViewById(R.id.btn);
		tv_tips = (TextView) main_view.findViewById(R.id.tv_tips);
		AnimUtil.createQueryAnimation(tv_tips, Constants.toastTips);
		add = (TextView) main_view.findViewById(R.id.add);
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

	class MyPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) { // 设置点的颜色
			case 0:
				// dot1.setTextColor(Color.WHITE);
				// dot2.setTextColor(Color.BLACK);
				break;

			case 1:
				// dot1.setTextColor(Color.BLACK);
				// dot2.setTextColor(Color.WHITE);
				// if (sb != null) {
				// if (sb.toString() != null) {
				// laps = sb.toString().split(",");
				// tv_totle_laps.setText(laps.length + "圈 记录");
				// }
				// } else {
				// laps = null;
				// tv_totle_laps.setText("暂无记录");
				// }
				// if (LogUtil.isDebug) {
				// Log.d(TAG,
				// "myLapsAdapter.getCount()"
				// + myLapsAdapter.getCount());
				// }
				// myLapsAdapter.notifyDataSetChanged();
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}
}
