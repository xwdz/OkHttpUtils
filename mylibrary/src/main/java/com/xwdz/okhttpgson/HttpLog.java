package com.xwdz.okhttpgson;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/3/28/028
 */
public class HttpLog implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.w(LOG.TAG, message);
    }
}
