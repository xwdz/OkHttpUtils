package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.method.MethodPut;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class MethodPutImpl extends BaseImpl implements MethodPut {

    private RequestBody mRequestBody;

    public MethodPutImpl(String url) {
        super(url);
    }

    @Override
    public Request buildRequest() {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> map : mParams.entrySet()) {
            params.add(map.getKey(), map.getValue());
        }

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        if (mRequestBody != null) {
            requestBuilder.put(mRequestBody);
        }

        requestBuilder
                .url(mUrl)
                .tag(mTag);

        return requestBuilder.build();
    }

    @Override
    public void requestBody(RequestBody body) {
        this.mRequestBody = body;
    }
}
