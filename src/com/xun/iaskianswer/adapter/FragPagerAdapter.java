package com.xun.iaskianswer.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.config.AnswerType;
import com.xun.iaskianswer.entity.response.AbstractResponse;
import com.xun.iaskianswer.fragment.InfoFragment;
import com.xun.iaskianswer.fragment.MainFragment;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class FragPagerAdapter extends FragmentStatePagerAdapter {
    private FragmentActivity mContext;
    public List<View> mListViews;
    private FragPagerAdapter mMyViewPager;
    private static final String TAG = "TestFragPagerAdapter";
    private boolean doNotifyDataSetChangedOnce = false;
    private AbstractResponse mResponse;
    private AnswerType type;

    public FragPagerAdapter(FragmentManager fm, FragmentActivity context, List<View> listViews) {
        super(fm);
        mContext = context;
        mListViews = listViews;
        mMyViewPager = this;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag;
        if (position == 0) {
            frag = new MainFragment(mContext, mListViews, mMyViewPager);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY, Constant.images[position % getCount() % 5]);
            frag = new InfoFragment(mMyViewPager, mResponse, position, type);
            frag.setArguments(bundle);
        }
        return frag;
    }

    public void addInfoView(View view) {
        doNotifyDataSetChangedOnce = true;
        mListViews.add(view);
    }

    public void resetInfoView() {
        doNotifyDataSetChangedOnce = true;
        if (mListViews.size() > 1) {
            for (int i = mListViews.size() - 1; i > 0; i--) {
                mListViews.remove(i);
            }
        }
    }

    public void addData(AbstractResponse mResponse, AnswerType type) {
        this.mResponse = mResponse;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }
        return mListViews.size();
    }

}
