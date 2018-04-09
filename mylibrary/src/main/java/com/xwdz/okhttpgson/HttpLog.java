package com.xwdz.okhttpgson;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/3/28
 */
public class HttpLog implements HttpLoggingInterceptor.Logger {

    private HttpManager.LogListener mLogListener;

    public HttpLog(HttpManager.LogListener logListener) {
        mLogListener = logListener;
    }

    @Override
    public void log(String message) {
        if (mLogListener == null) {
            Log.w(LOG.TAG, message);
        } else {
            Log.w(mLogListener.getHttpLogTag(), message);
        }
    }
}
