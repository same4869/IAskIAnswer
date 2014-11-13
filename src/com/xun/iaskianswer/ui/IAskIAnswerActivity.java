package com.xun.iaskianswer.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.chiemy.jellyviewpager.widget.JellyViewPager;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.TestFragPagerAdapter;
import com.xun.iaskianswer.util.PowerUtil;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class IAskIAnswerActivity extends FragmentActivity {
    private Boolean is2CallBack = false;// 是否双击退出
    private static final String TAG = "IAskIAnswerActivity";

    private JellyViewPager myViewPager; // 页卡内容
    private List<View> list; // 存放页卡
    private LayoutInflater inflater;
    private TestFragPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_main);

        initView();
    }

    private void initView() {
        inflater = getLayoutInflater();
        myViewPager = (JellyViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<View>();
        View main_view = inflater.inflate(R.layout.activity_iask_ianswer, null);
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
}
