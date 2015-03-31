package com.xun.iaskianswer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.FragPagerAdapter;
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

/**
 * 语音页面
 * 
 * @author xwang
 * 
 *         2014-11-13
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private TextView mResultTv;
    private ImageView mSpeechBtn;
    private TextView mTipsTv;
    private TextView mAddressTv;

    private DialogRecognitionListener mRecognitionListener;
    private VoiceRequestManager mVoiceRequestManager;
    private BaiduLocationManager mBaiduLocationManager;
    private RandomQueryThread mRandomQueryThread;
    private VoiceActionManager mVoiceActionManager;

    private Handler tipsHandler = new Handler();
    private String turingResult = null; // 图灵机器人返回回来的JSON
    private BaiduASRDigitalDialog mDialog = null;

    private FragmentActivity context;
    private FragPagerAdapter mMyPagerAdapter;
    private OnResultReciviedListener callback;

    public MainFragment(FragmentActivity context, List<View> listViews, FragPagerAdapter myPagerAdapter) {
        this.context = context;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnResultReciviedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    private void initData() {
        mVoiceRequestManager = VoiceRequestManager.getInstance();
        mBaiduLocationManager = BaiduLocationManager.getInstance();
        mVoiceActionManager = VoiceActionManager.getInstance();
        mBaiduLocationManager.startLocationUpdate(mAddressTv, context.getApplicationContext());
        if (mRandomQueryThread == null) {
            mRandomQueryThread = new RandomQueryThread();
            mRandomQueryThread.start();
        }
    }

    private void initUI(View root) {
        mResultTv = (TextView) root.findViewById(R.id.tv);
        mSpeechBtn = (ImageView) root.findViewById(R.id.btn);
        mTipsTv = (TextView) root.findViewById(R.id.tv_tips);
        AnimUtil.createQueryAnimation(mTipsTv, Constants.toastTips);
        mAddressTv = (TextView) root.findViewById(R.id.add);
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
                            Boolean isVoiceAction = mVoiceActionManager.isVoiceAction(context.getApplicationContext(),
                                    rs.get(0));
                            if (isVoiceAction == true) {

                            } else {
                                turingResult = HttpUtil.httpGetUtil(rs.get(0));
                                Log.d(TAG, "turingResult  --> " + turingResult);
                                hanlder.sendEmptyMessage(0);
                            }
                            super.run();
                        }
                    }.start();
                }

            }
        };
        mSpeechBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mResultTv.setText(null);
                mVoiceRequestManager.initVoiceParams(context, mDialog, mRecognitionListener);
                mVoiceRequestManager.showVoiceDialog(mDialog);
            }
        });
    }

    // 图灵机器人oneBox分支Handler
    Handler hanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (turingResult != null) {
                String SEARCH_TYPE = null; // 截取出来的返回类型码
                ResponseManager responseManager;
                responseManager = ResponseManager.getInstance();
                if (turingResult.charAt(8) != '4') {
                    SEARCH_TYPE = turingResult.substring(8, 14);
                } else {
                    SEARCH_TYPE = turingResult.substring(8, 13);
                }
                if (LogUtil.is_debug) {
                    Log.d(TAG,
                            "SEARCH_TYPE --> " + SEARCH_TYPE + " turingResult.charAt(8) --> " + turingResult.charAt(8));
                }
                int type = Integer.parseInt(SEARCH_TYPE);
                AnswerType type2 = AnswerType.fromInt(type);
                AbstractResponse mResponse = responseManager.productResponse(turingResult, type2);
                if (mResponse instanceof TextResponse) {
                    TextResponse mTextResponse = (TextResponse) mResponse;
                    mResultTv.setText(mTextResponse.text);
                } else if (mResponse instanceof UrlResponse) {
                    UrlResponse mUrlResponse = (UrlResponse) mResponse;
                } else if (mResponse instanceof NovelResponse) {

                } else if (mResponse instanceof NewsResponse) {

                } else if (mResponse instanceof AppResponse) {

                } else if (mResponse instanceof TrainResponse) {
                    TrainResponse mTrainResponse = (TrainResponse) mResponse;
                    mResultTv.setText(mTrainResponse.text + " ~向右滑啊滑啊滑啊~");
                } else if (mResponse instanceof FlightResponse) {
                    FlightResponse mFlightResponse = (FlightResponse) mResponse;
                    mResultTv.setText(mFlightResponse.text + " ~向右滑啊滑啊滑啊~");
                } else if (mResponse instanceof GroupResponse) {

                } else if (mResponse instanceof MovieResponse) {

                } else if (mResponse instanceof HotelResponse) {

                } else if (mResponse instanceof LotteryResponse) {

                } else if (mResponse instanceof PriceResponse) {

                } else if (mResponse instanceof FoodResponse) {

                } else {

                }
                // mResultTv.setText(turingResult);
                // 回调到activity中去，实现fragment的通信
                callback.onResult(mResponse, type2);
            }
            super.handleMessage(msg);
        }

    };

    private class RandomQueryThread extends Thread {
        @Override
        public void run() {
            while (true) {
                tipsHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AnimUtil.createQueryAnimation(mTipsTv, Constants.toastTips);
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
        public void onResult(AbstractResponse mResponse, AnswerType type2);
    }
}
