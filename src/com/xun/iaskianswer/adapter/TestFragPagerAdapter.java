package com.xun.iaskianswer.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.fragment.InfoFragment;
import com.xun.iaskianswer.fragment.MainFragment;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class TestFragPagerAdapter extends FragmentPagerAdapter {
    private FragmentActivity mContext;
    public List<View> mListViews;

    public TestFragPagerAdapter(FragmentManager fm, FragmentActivity context, List<View> listViews) {
        super(fm);
        mContext = context;
        mListViews = listViews;
    }

    @Override
    public Fragment getItem(int arg0) {
        Fragment frag;
        if (arg0 == 0) {
            frag = new MainFragment(mContext);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY, Constant.images[arg0 % getCount()]);
            frag = new InfoFragment();
            frag.setArguments(bundle);
        }
        return frag;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mListViews.get(arg1));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        return mListViews.get(arg1);
    }
}
