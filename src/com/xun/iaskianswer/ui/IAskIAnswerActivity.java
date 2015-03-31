package com.xun.iaskianswer.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.FragPagerAdapter;
import com.xun.iaskianswer.base.BaseFragmentActivity;
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
import com.xun.iaskianswer.entity.response.TrainResponse;
import com.xun.iaskianswer.entity.response.UrlResponse;
import com.xun.iaskianswer.fragment.MainFragment.OnResultReciviedListener;
import com.xun.iaskianswer.manager.ResponseManager;
import com.xun.iaskianswer.util.PowerUtil;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class IAskIAnswerActivity extends BaseFragmentActivity implements OnResultReciviedListener {
    private static final String TAG = "IAskIAnswerActivity";
    private String SEARCH_TYPE = null; // 截取出来的返回类型码

    private ViewPager mViewPager; // 页卡内容
    private List<View> mList; // 存放页卡
    private LayoutInflater mInflater;
    private FragPagerAdapter mPagerAdapter;
    private Boolean is2CallBack = false;// 是否双击退出
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
        mInflater = getLayoutInflater();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mList = new ArrayList<View>();
        View main_view = mInflater.inflate(R.layout.fragment_main, null);
        mList.add(main_view);
        mPagerAdapter = new FragPagerAdapter(getSupportFragmentManager(), IAskIAnswerActivity.this, mList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
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
    public void onResult(AbstractResponse mResponse, AnswerType type2) {

        if (mResponse instanceof UrlResponse) {
            UrlResponse mUrlResponse = (UrlResponse) mResponse;
        } else if (mResponse instanceof NovelResponse) {

        } else if (mResponse instanceof NewsResponse) {

        } else if (mResponse instanceof AppResponse) {

        } else if (mResponse instanceof TrainResponse) {
            mPagerAdapter.resetInfoView();
            mPagerAdapter.notifyDataSetChanged();
            TrainResponse mTrainResponse = (TrainResponse) mResponse;
            View info_view = mInflater.inflate(R.layout.fragment_info, null);
            for (int i = 0; i < mTrainResponse.list.size(); i++) {
                mPagerAdapter.addInfoView(info_view);
            }
            mPagerAdapter.addData(mResponse, type2);
            mPagerAdapter.notifyDataSetChanged();

        } else if (mResponse instanceof FlightResponse) {
            mPagerAdapter.resetInfoView();
            mPagerAdapter.notifyDataSetChanged();
            FlightResponse mFlightResponse = (FlightResponse) mResponse;
            View info_view = mInflater.inflate(R.layout.fragment_info, null);
            for (int i = 0; i < mFlightResponse.list.size(); i++) {
                mPagerAdapter.addInfoView(info_view);
            }
            mPagerAdapter.addData(mResponse, type2);
            mPagerAdapter.notifyDataSetChanged();

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
