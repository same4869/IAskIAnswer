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
import android.widget.Button;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.FragPagerAdapter;
import com.xun.iaskianswer.config.Constants;
import com.xun.iaskianswer.manager.BaiduLocationManager;
import com.xun.iaskianswer.manager.VoiceActionManager;
import com.xun.iaskianswer.manager.VoiceRequestManager;
import com.xun.iaskianswer.util.AnimUtil;
import com.xun.iaskianswer.util.HttpUtil;

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
    private Button mSpeechBtn;
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
        mSpeechBtn = (Button) root.findViewById(R.id.btn);
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
            mResultTv.setText(turingResult);
            // 回调到activity中去，实现fragment的通信
            callback.onResult(turingResult);
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
        public void onResult(String turingResult);
    }
}
