package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.ICallBack;
import com.xwdz.okhttpgson.method.MethodPost;
import com.xwdz.okhttpgson.method.OkHttpRequest;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class MethodPostImpl extends BaseImpl implements MethodPost {


    private String mUrl;
    private String mTag;
    private Request mRequest;


    public MethodPostImpl(String url) {
        this.mUrl = url;
        this.mTag = url;

    }

    @Override
    public Response execute() throws IOException {
        if (mRequest == null){
            mRequest = buildPostRequest();
        }
        return super.execute(mRequest);
    }

    @Override
    public void execute(ICallBack ICallBack) {
        if (mRequest == null){
            mRequest = buildPostRequest();
        }
        super.execute(mRequest, ICallBack);
    }

    @Override
    public void cancel() {
        super.cancel();
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

    private void create() {
        mRequest = buildPostRequest();
    }

    private Request buildPostRequest() {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> map : mParams.entrySet()) {
            params.add(map.getKey(), map.getValue());
        }
        requestBuilder.url(mUrl).post(params.build());
        requestBuilder.tag(mTag);
        return requestBuilder.build();
    }

}
