package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.callback.ICallBack;
import com.xwdz.okhttpgson.OkRun;
import com.xwdz.okhttpgson.method.Request;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/4/2
 */
public abstract class BaseImpl implements Request {

    private OkRun mOkRun;
    final LinkedHashMap<String, String> mParams;
    final LinkedHashMap<String, String> mHeaders;
    final String mUrl;

    private okhttp3.Request mRequest;
    private boolean mIsCallbackToMainUIThread;

    String mTag;

    BaseImpl(String url) {
        this.mUrl = url;
        this.mOkRun = OkRun.getInstance();
        this.mParams = new LinkedHashMap<>();
        this.mHeaders = new LinkedHashMap<>();
    }

    @Override
    public Response execute() throws IOException {
        if (mRequest == null) {
            mRequest = buildRequest();
        }

        return mOkRun.execute(mRequest);
    }

    @Override
    public void execute(ICallBack callBack) {
        if (mRequest == null) {
            mRequest = buildRequest();
        }

        mOkRun.execute(mRequest, callBack, mIsCallbackToMainUIThread);
    }

    @Override
    public void cancel() {
        this.mOkRun.cancel();
    }


    @Override
    public Request addParams(String key, String value) {
        assertKeyValue(key, value);
        mParams.put(key, value);
        return this;
    }


    @Override
    public Request addParams(LinkedHashMap<String, String> map) {
        mParams.clear();
        mParams.putAll(map);
        return this;
    }

    @Override
    public Request addHeaders(String key, String value) {
        assertKeyValue(key, value);
        mHeaders.put(key, value);
        return this;
    }

    @Override
    public Request setCallBackToMainUIThread(boolean isMainUIThread) {
        this.mIsCallbackToMainUIThread = isMainUIThread;
        return this;
    }

    @Override
    public Request addHeaders(LinkedHashMap<String, String> headers) {
        mHeaders.putAll(headers);
        return this;
    }

    @Override
    public Request setTag(String tag) {
        this.mTag = tag;
        return this;
    }

    @Override
    public okhttp3.Request getRequest() {
        return mRequest;
    }

    public abstract okhttp3.Request buildRequest();


    /*      */
    void assertKeyValue(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key == null");
        }

        if (TextUtils.isEmpty(value)) {
            throw new NullPointerException("value == null");
        }
    }
}
