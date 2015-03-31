package com.xun.iaskianswer.base;

import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author wangxun
 * 
 *         2015-3-31
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainActivity"); // 保证 onPageEnd 在onPause
                                                 // 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainActivity"); // 统计页面
        MobclickAgent.onResume(this);
    }

}
