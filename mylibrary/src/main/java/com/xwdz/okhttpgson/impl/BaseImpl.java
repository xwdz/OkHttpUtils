package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.HttpManager;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/4/2
 */
public abstract class BaseImpl {

    private HttpManager mHttpManager;
    final LinkedHashMap<String, String> mParams;
    final LinkedHashMap<String, String> mHeaders;

    BaseImpl() {
        mHttpManager = HttpManager.getInstance();
        this.mParams = new LinkedHashMap<>();
        this.mHeaders = new LinkedHashMap<>();
    }


    Response execute(Request request) throws IOException {
        return mHttpManager.execute(request);
    }

    void execute(Request request, CallBack callBack) {
        mHttpManager.execute(request, callBack);
    }

    protected void cancel() {
        this.mHttpManager.cancel();
    }

    void assertKeyValue(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key == null");
        }

        if (TextUtils.isEmpty(value)) {
            throw new NullPointerException("value == null");
        }
    }
}
