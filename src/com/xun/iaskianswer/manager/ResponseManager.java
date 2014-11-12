package com.xun.iaskianswer.manager;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.adapter.MyPagerAdapter;
import com.xun.iaskianswer.app.IAskIAnswerApp;
import com.xun.iaskianswer.config.AnswerType;
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
import com.xun.iaskianswer.util.JSONUtil;

/**
 * @author wangxun
 * 
 *         2014-10-31
 */
// 图灵机器人返回结果manager
public class ResponseManager {
    private static ResponseManager instance = null;

    private ResponseManager() {

    }

    public static ResponseManager getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new ResponseManager();
        }
    }

    // 工厂类，给返回结果和类型，生成对应类型的object
    public AbstractResponse productResponse(String turingResult, AnswerType type) {
        switch (type) {
        case TEXT:
            TextResponse textResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return textResponse;
        case URL:
            UrlResponse urlResponse = JSONUtil.objectFromJSONString(turingResult, UrlResponse.class);
            return urlResponse;
        case NOVEL:
            NovelResponse novelResponse = JSONUtil.objectFromJSONString(turingResult, NovelResponse.class);
            return novelResponse;
        case NEWS:
            NewsResponse newsResponse = JSONUtil.objectFromJSONString(turingResult, NewsResponse.class);
            return newsResponse;
        case APP:
            AppResponse appResponse = JSONUtil.objectFromJSONString(turingResult, AppResponse.class);
            return appResponse;
        case TRAIN:
            TrainResponse trainResponse = JSONUtil.objectFromJSONString(turingResult, TrainResponse.class);
            return trainResponse;
        case FLIGHT:
            FlightResponse flightResponse = JSONUtil.objectFromJSONString(turingResult, FlightResponse.class);
            return flightResponse;
        case GROUP:
            GroupResponse groupResponse = JSONUtil.objectFromJSONString(turingResult, GroupResponse.class);
            return groupResponse;
        case MOVIE:
            MovieResponse movieResponse = JSONUtil.objectFromJSONString(turingResult, MovieResponse.class);
            return movieResponse;
        case HOTEL:
            HotelResponse hotelResponse = JSONUtil.objectFromJSONString(turingResult, HotelResponse.class);
            return hotelResponse;
        case LOTTERY:
            LotteryResponse lotteryResponse = JSONUtil.objectFromJSONString(turingResult, LotteryResponse.class);
            return lotteryResponse;
        case PRICE:
            PriceResponse priceResponse = JSONUtil.objectFromJSONString(turingResult, PriceResponse.class);
            return priceResponse;
        case FOOD:
            FoodResponse foodResponse = JSONUtil.objectFromJSONString(turingResult, FoodResponse.class);
            return foodResponse;
        case KEY_LENGTH_ERROR:
            TextResponse keyLengthResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return keyLengthResponse;
        case REQUEST_NULL:
            TextResponse requestNullResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return requestNullResponse;
        case KEY_ERROR:
            TextResponse keyErrorResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return keyErrorResponse;
        case NO_REQUEST_COUNT:
            TextResponse noRequstCountResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return noRequstCountResponse;
        case NO_SUPPORT:
            TextResponse noSupportResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return noSupportResponse;
        case UPDATING:
            TextResponse upDatingResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return upDatingResponse;
        case FORMAT_ERROR:
            TextResponse formatErrorResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return formatErrorResponse;
        default:
            TextResponse defaultResponse = JSONUtil.objectFromJSONString(turingResult, TextResponse.class);
            return defaultResponse;
        }
    }

    public void notifyViewPagerDataChanged(AbstractResponse mResponse, final Activity activity, List<View> list,
            TextView tv1, TextView tv2, TextView tv3, TextView tv4, MyPagerAdapter myPagerAdapte,
            NetworkImageView networkImageView, ImageView imageView) {
        if (mResponse instanceof TextResponse) {
        } else if (mResponse instanceof UrlResponse) {
        } else if (mResponse instanceof NovelResponse) {

        } else if (mResponse instanceof NewsResponse) {

        } else if (mResponse instanceof AppResponse) {

        } else if (mResponse instanceof TrainResponse) {

        } else if (mResponse instanceof FlightResponse) {
            FlightResponse mFlightResponse = (FlightResponse) mResponse;
            if (list != null) {
                if (list.size() > 1) {
                    for (int j = list.size() - 1; j > 0; j--) {
                        list.remove(j);
                    }
                }

                for (int i = 0; i < mFlightResponse.list.size(); i++) {
                    LayoutInflater inflater = activity.getLayoutInflater();
                    View card_view = inflater.inflate(R.layout.card_result, null);

                    tv1 = (TextView) card_view.findViewById(R.id.textView1);
                    tv2 = (TextView) card_view.findViewById(R.id.textView2);
                    tv3 = (TextView) card_view.findViewById(R.id.textView3);
                    tv4 = (TextView) card_view.findViewById(R.id.textView4);
                    tv1.setText(mFlightResponse.list.get(i).flight);
                    tv2.setText(mFlightResponse.list.get(i).route);
                    tv3.setText(mFlightResponse.list.get(i).starttime);
                    tv4.setText(mFlightResponse.list.get(i).endtime);
                    list.add(card_view);
                }
            }
            myPagerAdapte.notifyDataSetChanged();

        } else if (mResponse instanceof GroupResponse) {

        } else if (mResponse instanceof MovieResponse) {
            final MovieResponse mMovieResponse = (MovieResponse) mResponse;
            if (list != null) {
                if (list.size() > 1) {
                    for (int j = list.size() - 1; j > 0; j--) {
                        list.remove(j);
                    }
                }

                for (int i = 0; i < mMovieResponse.list.size(); i++) {
                    LayoutInflater inflater = activity.getLayoutInflater();
                    View card_view = inflater.inflate(R.layout.card_result, null);

                    tv1 = (TextView) card_view.findViewById(R.id.textView1);
                    tv2 = (TextView) card_view.findViewById(R.id.textView2);
                    networkImageView = (NetworkImageView) card_view.findViewById(R.id.netWorkTestView);
                    imageView = (ImageView) card_view.findViewById(R.id.detailImage);
                    tv1.setText(mMovieResponse.list.get(i).name);
                    tv2.setText(mMovieResponse.list.get(i).info);
                    String tmp = mMovieResponse.list.get(i).detailurl;

                    networkImageView.setDefaultImageResId(R.drawable.ic_launcher);
                    imageView.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(mMovieResponse.list.get(0).detailurl));
                            it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.getApplicationContext().startActivity(it);
                        }
                    });
                    networkImageView.setErrorImageResId(R.drawable.ic_launcher);
                    networkImageView.setImageUrl(mMovieResponse.list.get(i).icon, IAskIAnswerApp.getInstance()
                            .getImageLoader());
                    list.add(card_view);
                }
            }
            myPagerAdapte.notifyDataSetChanged();

        } else if (mResponse instanceof HotelResponse) {

        } else if (mResponse instanceof LotteryResponse) {

        } else if (mResponse instanceof PriceResponse) {

        } else if (mResponse instanceof FoodResponse) {

        } else {

        }
    }
}
