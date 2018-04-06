package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.callback.ICallBack;
import com.xwdz.okhttpgson.HttpManager;
import com.xwdz.okhttpgson.method.OkHttpRequest;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/4/2
 */
public abstract class BaseImpl implements OkHttpRequest {

    private HttpManager mHttpManager;
    final LinkedHashMap<String, String> mParams;
    final LinkedHashMap<String, String> mHeaders;
    final String mUrl;

    private Request mRequest;
    String mTag;

    BaseImpl(String url) {
        this.mUrl = url;
        this.mHttpManager = HttpManager.getInstance();
        this.mParams = new LinkedHashMap<>();
        this.mHeaders = new LinkedHashMap<>();
    }

    @Override
    public Response execute() throws IOException {
        if (mRequest == null) {
            mRequest = buildRequest();
        }

        return mHttpManager.execute(mRequest);
    }

    @Override
    public void execute(ICallBack callBack) {
        if (mRequest == null) {
            mRequest = buildRequest();
        }

        mHttpManager.execute(mRequest, callBack);
    }

    @Override
    public void cancel() {
        this.mHttpManager.cancel();
    }


    @Override
    public OkHttpRequest addParams(String key, String value) {
        assertKeyValue(key, value);
        mParams.put(key, value);
        return this;
    }


    @Override
    public OkHttpRequest addParams(LinkedHashMap<String, String> map) {
        mParams.clear();
        mParams.putAll(map);
        return this;
    }

    @Override
    public OkHttpRequest addHeaders(String key, String value) {
        assertKeyValue(key, value);
        mHeaders.put(key, value);
        return this;
    }

    @Override
    public OkHttpRequest addHeaders(LinkedHashMap<String, String> headers) {
        mHeaders.putAll(headers);
        return this;
    }

    @Override
    public OkHttpRequest setTag(String tag) {
        this.mTag = tag;
        return this;
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

    public abstract Request buildRequest();


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
