package com.xwdz.test;

import android.app.Application;

import com.xwdz.okhttpgson.HttpManager;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/4/3
 */
public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.getInstance().setOkHttpClient();
    }
}
