package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.method.MethodPost;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class MethodPostImpl extends BaseImpl implements MethodPost {


    public MethodPostImpl(String url) {
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


        requestBuilder
                .url(mUrl)
                .tag(mTag)
                .post(params.build());

        return requestBuilder.build();
    }
}
