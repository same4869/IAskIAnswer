package com.xun.iaskianswer.app;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.xun.iaskianswer.cache.LruImageCache;

public class IAskIAnswerApp extends Application {
    public static Context AppContext;
    private static IAskIAnswerApp instance;
    private RequestQueue mQueue;
    private ImageLoader imageLoader;

    public static IAskIAnswerApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        AppContext = this;
        instance = this;
        super.onCreate();

        mQueue = Volley.newRequestQueue(this);
        LruImageCache lruImageCache = LruImageCache.instance();
        imageLoader = new ImageLoader(mQueue, lruImageCache);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
