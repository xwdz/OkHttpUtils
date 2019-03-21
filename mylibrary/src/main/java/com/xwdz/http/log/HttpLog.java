package com.xwdz.http.log;

import android.text.TextUtils;
import android.util.Log;


/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/3/28
 */
public class HttpLog implements HttpLoggingInterceptor.Logger {

    private String mTag;

    public HttpLog(String tag) {
        this.mTag = tag;
    }

    @Override
    public void log(String message) {
        if (TextUtils.isEmpty(mTag)) {
            Log.w("XHttp", message);
        } else {
            Log.w(mTag, message);
        }
    }
}
