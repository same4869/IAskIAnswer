package com.xun.iaskianswer.fragment;

import java.util.ArrayList;

import android.content.Context;
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
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.xun.iaskianswer.R;
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

    private ResponseManager responseManager;
    private VoiceActionManager voiceActionManager;

    private String turingResult = null; // 图灵机器人返回回来的JSON
    private String SEARCH_TYPE = null; // 截取出来的返回类型码
    private BaiduASRDigitalDialog mDialog = null;

    private Context context;
    private static final String TAG = "MainFragment";

    public MainFragment(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_iask_ianswer, null);
        initUI(rootView);
        initSpeechListener();
        initData();
        return rootView;
    }

    private void initData() {
        voiceRequestManager = VoiceRequestManager.getInstance();
        baiduLocationManager = BaiduLocationManager.getInstance();
        voiceActionManager = VoiceActionManager.getInstance();
        responseManager = ResponseManager.getInstance();
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
            // Toast.makeText(IAskIAnswerActivity.this,
            // turingResult.substring(8, 14), Toast.LENGTH_SHORT).show();
            // 返回码以4开头的只有5位，其他的为6位，这里要判断一下
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
                tv_tips.setText(mTextResponse.text);
            } else if (mResponse instanceof UrlResponse) {
                UrlResponse mUrlResponse = (UrlResponse) mResponse;
                Toast.makeText(context.getApplicationContext(), mUrlResponse.url, Toast.LENGTH_SHORT).show();
            } else if (mResponse instanceof NovelResponse) {

            } else if (mResponse instanceof NewsResponse) {

            } else if (mResponse instanceof AppResponse) {

            } else if (mResponse instanceof TrainResponse) {

            } else if (mResponse instanceof FlightResponse) {
                FlightResponse mFlightResponse = (FlightResponse) mResponse;
                // responseManager.notifyViewPagerDataChanged(mFlightResponse,
                // IAskIAnswerActivity.this, list, textView1,
                // textView2, textView3, textView4, myPagerAdapter, null, null);

            } else if (mResponse instanceof GroupResponse) {

            } else if (mResponse instanceof MovieResponse) {
                MovieResponse mMovieResponse = (MovieResponse) mResponse;
                // responseManager.notifyViewPagerDataChanged(mMovieResponse,
                // IAskIAnswerActivity.this, list, textView1,
                // textView2, null, null, myPagerAdapter, networkImageView,
                // imageView);

            } else if (mResponse instanceof HotelResponse) {

            } else if (mResponse instanceof LotteryResponse) {

            } else if (mResponse instanceof PriceResponse) {

            } else if (mResponse instanceof FoodResponse) {

            } else {

            }
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
        super.onDestroy();
    }
}
