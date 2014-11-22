package com.xun.iaskianswer.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.fragment.InfoFragment;
import com.xun.iaskianswer.fragment.MainFragment;
import com.xun.iaskianswer.util.LogUtil;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class TestFragPagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity mContext;
	public List<View> mListViews;
	private TestFragPagerAdapter mMyViewPager;
	private static final String TAG = "TestFragPagerAdapter";

	public TestFragPagerAdapter(FragmentManager fm, FragmentActivity context, List<View> listViews) {
		super(fm);
		mContext = context;
		mListViews = listViews;
		mMyViewPager = this;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment frag;
		if (arg0 == 0) {
			frag = new MainFragment(mContext, mListViews, mMyViewPager);
		} else {
			Bundle bundle = new Bundle();
			bundle.putInt(Constant.KEY, Constant.images[arg0 % getCount()]);
			frag = new InfoFragment(mMyViewPager);
			frag.setArguments(bundle);
		}
		return frag;
	}

	@Override
	public int getCount() {
		if (LogUtil.is_debug) {
			Log.d(TAG, "mListViews.size() --> " + mListViews.size());
		}
		return mListViews.size();
	}

}
