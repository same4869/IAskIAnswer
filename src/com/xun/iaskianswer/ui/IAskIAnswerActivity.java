package com.xun.iaskianswer.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.chiemy.jellyviewpager.widget.JellyViewPager;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.TestFragPagerAdapter;
import com.xun.iaskianswer.config.AnswerType;
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
import com.xun.iaskianswer.fragment.MainFragment.OnResultReciviedListener;
import com.xun.iaskianswer.manager.ResponseManager;
import com.xun.iaskianswer.util.LogUtil;
import com.xun.iaskianswer.util.PowerUtil;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class IAskIAnswerActivity extends FragmentActivity implements OnResultReciviedListener {
	private Boolean is2CallBack = false;// 是否双击退出
	private static final String TAG = "IAskIAnswerActivity";

	private JellyViewPager myViewPager; // 页卡内容
	private List<View> list; // 存放页卡
	private LayoutInflater inflater;
	private TestFragPagerAdapter myPagerAdapter;

	private String SEARCH_TYPE = null; // 截取出来的返回类型码
	private ResponseManager responseManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_main);

		initView();
		initData();
	}

	private void initData() {
		responseManager = ResponseManager.getInstance();
	}

	private void initView() {
		inflater = getLayoutInflater();
		myViewPager = (JellyViewPager) findViewById(R.id.viewPager);
		list = new ArrayList<View>();
		View main_view = inflater.inflate(R.layout.fragment_main, null);
		list.add(main_view);
		myPagerAdapter = new TestFragPagerAdapter(getSupportFragmentManager(), IAskIAnswerActivity.this, list);
		myViewPager.setAdapter(myPagerAdapter);
		myViewPager.setOnPageChangeListener(new MyPagerChangeListener());
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

	@Override
	public void onResult(String turingResult) {
		if (turingResult.charAt(8) != '4') {
			SEARCH_TYPE = turingResult.substring(8, 14);
		} else {
			SEARCH_TYPE = turingResult.substring(8, 13);
		}
		if (LogUtil.is_debug) {
			Log.d(TAG, "SEARCH_TYPE --> " + SEARCH_TYPE + " turingResult.charAt(8) --> " + turingResult.charAt(8));
		}
		int type = Integer.parseInt(SEARCH_TYPE);
		AnswerType type2 = AnswerType.fromInt(type);
		AbstractResponse mResponse = responseManager.productResponse(turingResult, type2);
		if (mResponse instanceof TextResponse) {
			TextResponse mTextResponse = (TextResponse) mResponse;
			// tv_tips.setText(mTextResponse.text);
		} else if (mResponse instanceof UrlResponse) {
			UrlResponse mUrlResponse = (UrlResponse) mResponse;
		} else if (mResponse instanceof NovelResponse) {

		} else if (mResponse instanceof NewsResponse) {

		} else if (mResponse instanceof AppResponse) {

		} else if (mResponse instanceof TrainResponse) {

		} else if (mResponse instanceof FlightResponse) {
			FlightResponse mFlightResponse = (FlightResponse) mResponse;
			for (int i = 0; i < mFlightResponse.list.size(); i++) {
				View info_view = inflater.inflate(R.layout.fragment_info, null);
				list.add(info_view);
			}
			myPagerAdapter.notifyDataSetChanged();
			// responseManager.notifyViewPagerDataChanged(mFlightResponse,
			// context, mListViews, mMyPagerAdapter);

		} else if (mResponse instanceof GroupResponse) {

		} else if (mResponse instanceof MovieResponse) {
			MovieResponse mMovieResponse = (MovieResponse) mResponse;
			// responseManager.notifyViewPagerDataChanged(mMovieResponse,
			// context, mListViews, mMyPagerAdapter);

		} else if (mResponse instanceof HotelResponse) {

		} else if (mResponse instanceof LotteryResponse) {

		} else if (mResponse instanceof PriceResponse) {

		} else if (mResponse instanceof FoodResponse) {

		} else {

		}

	}
}
