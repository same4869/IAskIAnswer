package com.xun.iaskianswer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.TestFragPagerAdapter;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class InfoFragment extends Fragment {
    boolean visible = true;
    TestFragPagerAdapter myPagerAdapter;

    public InfoFragment(TestFragPagerAdapter myPagerAdapter) {
        this.myPagerAdapter = myPagerAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_layout, container, false);
        initUI(rootView);
        myPagerAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void initUI(View root) {

        final View tv1 = root.findViewById(R.id.textView1);
        final View tv2 = root.findViewById(R.id.textView2);
        ImageView iv = (ImageView) root.findViewById(R.id.imageView1);
        Bundle bundle = getArguments();
        int res = bundle.getInt(Constant.KEY, R.drawable.a);
        iv.setImageResource(res);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!visible) {
                    visible = true;
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                } else {
                    visible = false;
                    tv1.setVisibility(View.INVISIBLE);
                    tv2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        myPagerAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

}
