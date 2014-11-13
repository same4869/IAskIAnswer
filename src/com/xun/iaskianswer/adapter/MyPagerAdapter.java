package com.xun.iaskianswer.adapter;

import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.fragment.InfoFragment;

/**
 * @author xwang
 * 
 *         2014年10月25日
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    public List<View> mListViews;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        // this.mListViews = mListViews;
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
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        // return mListViews.size();
        return 5;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY, Constant.images[arg0 % getCount()]);
        Fragment frag = new InfoFragment();
        frag.setArguments(bundle);
        return frag;
    }

}
