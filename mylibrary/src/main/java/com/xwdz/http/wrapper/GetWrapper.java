package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;
import com.xwdz.http.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class GetWrapper extends BaseWrapper<GetWrapper> {


    private LinkedHashMap<String, String> mHeaders = new LinkedHashMap<>();
    private LinkedHashMap<String, String> mParams  = new LinkedHashMap<>();

    private String mUrl;
    private String mTag;
    private boolean mCallbackToMainUIThread = true;

    public GetWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient);
        mHeaders.clear();
        mParams.clear();

        mUrl = url;
        mTag = url;
    }


    @Override
    protected Request buildRequest() {
        Assert.checkNull(mUrl, "GET 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();
        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl + StringUtils.appendHttpParams(mParams));

        requestBuilder
                .tag(mTag);
        return requestBuilder.build();
    }


    @Override
    public GetWrapper tag(Object object) {
        Assert.checkNull(object, "tag not null!");
        mTag = String.valueOf(object);
        return this;
    }

    @Override
    public GetWrapper addHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    @Override
    public GetWrapper addParams(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    @Override
    public GetWrapper addParams(LinkedHashMap<String, String> params) {
        mParams.putAll(params);
        return this;
    }

    @Override
    public GetWrapper addHeader(LinkedHashMap<String, String> header) {
        mHeaders.putAll(header);
        return this;
    }

    @Override
    public boolean isCallbackMainUIThread() {
        return mCallbackToMainUIThread;
    }

    @Override
    public GetWrapper setCallbackMainUIThread(boolean isCallbackToMainUIThread) {
        mCallbackToMainUIThread = isCallbackToMainUIThread;
        return this;
    }
}
