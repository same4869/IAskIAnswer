package com.xun.iaskianswer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author wangxun
 * 
 *         2015-3-30
 */
public class InfoDetailListView extends ListView {

    public InfoDetailListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InfoDetailListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoDetailListView(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
