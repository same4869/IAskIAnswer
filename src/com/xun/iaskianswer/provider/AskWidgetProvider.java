package com.xun.iaskianswer.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.xun.iaskianswer.R;
import com.xun.iaskianswer.ui.IAskIAnswerActivity;

/**
 * 桌面widget
 * 
 * @author wangxun
 * 
 *         2015-3-27
 */
public class AskWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ask_widget_layout);
        Intent intent = new Intent(context, IAskIAnswerActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_btn, pi);
        appWidgetManager.updateAppWidget(appWidgetIds[0], remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
