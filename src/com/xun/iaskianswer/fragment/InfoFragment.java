package com.xun.iaskianswer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiemy.jellyviewpager.util.Constant;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.FragPagerAdapter;
import com.xun.iaskianswer.config.AnswerType;
import com.xun.iaskianswer.entity.response.AbstractResponse;
import com.xun.iaskianswer.entity.response.FlightResponse;
import com.xun.iaskianswer.entity.response.TrainResponse;

/**
 * @author xwang
 * 
 *         2014-11-13
 */
public class InfoFragment extends Fragment {
    boolean visible = true;
    FragPagerAdapter myPagerAdapter;
    private AbstractResponse mResponse;
    private int position;
    private AnswerType type;

    public InfoFragment(FragPagerAdapter myPagerAdapter, AbstractResponse mResponse, int position, AnswerType type) {
        this.myPagerAdapter = myPagerAdapter;
        this.mResponse = mResponse;
        this.position = position - 1;// 因为第一个page始终都是语音页
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        initUI(rootView);
        myPagerAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void initUI(View root) {

        final TextView titleTv = (TextView) root.findViewById(R.id.title_tv);
        final TextView detailTv = (TextView) root.findViewById(R.id.detail_tv);
        ImageView backgroundIv = (ImageView) root.findViewById(R.id.background_iv);
        Bundle bundle = getArguments();
        int res = bundle.getInt(Constant.KEY, R.drawable.a);
        backgroundIv.setImageResource(res);
        backgroundIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!visible) {
                    visible = true;
                    titleTv.setVisibility(View.VISIBLE);
                    detailTv.setVisibility(View.VISIBLE);
                } else {
                    visible = false;
                    titleTv.setVisibility(View.INVISIBLE);
                    detailTv.setVisibility(View.INVISIBLE);
                }
            }
        });
        switch (type) {
        case FLIGHT:
            titleTv.setText(((FlightResponse) mResponse).list.get(position).flight);
            detailTv.setText(((FlightResponse) mResponse).list.get(position).starttime + "---"
                    + ((FlightResponse) mResponse).list.get(position).endtime);
            break;
        case TRAIN:
            titleTv.setText(((TrainResponse) mResponse).content.get(position).trainnum);
            detailTv.setText(((TrainResponse) mResponse).content.get(position).start + "---"
                    + ((TrainResponse) mResponse).content.get(position).terminal + "/n"
                    + ((TrainResponse) mResponse).content.get(position).starttime + "---"
                    + ((TrainResponse) mResponse).content.get(position).endtime);
        default:
            break;
        }

    }

    @Override
    public void onDestroy() {
        myPagerAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

}
