package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.method.MethodPut;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class MethodPutImpl extends BaseImpl implements MethodPut {

    private RequestBody mRequestBody;
    private okhttp3.MediaType mMediaType;
    private String mPath;

    private String mType;

    public MethodPutImpl(String url) {
        super(url);
    }

    public MethodPutImpl(String url, String mediaType) {
        super(url);
        this.mType = mediaType;
    }

    @Override
    public Request buildRequest() {
        Request.Builder requestBuilder = new Request.Builder();
        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        if (!TextUtils.isEmpty(mType)) {
            if (OkHttpRun.MediaType.IMG.equals(mType)) {
                File file = new File(mPath);
                mMediaType = okhttp3.MediaType.parse(OkHttpRun.MediaType.IMG);
                mRequestBody = RequestBody.create(mMediaType, file);
            } else if (OkHttpRun.MediaType.PNG.equals(mType)) {
                File file = new File(mPath);
                mMediaType = okhttp3.MediaType.parse(OkHttpRun.MediaType.PNG);
                mRequestBody = RequestBody.create(mMediaType, file);
            }
        } else {
            //todo
        }

        requestBuilder
                .url(mUrl)
                .put(mRequestBody)
                .tag(mTag);

        return requestBuilder.build();
    }

    @Override
    public void requestBody(RequestBody body) {
        this.mRequestBody = body;
    }

    @Override
    public void mediaType(okhttp3.MediaType type) {
        this.mMediaType = type;
    }

    @Override
    public void path(String path) {
        this.mPath = path;
    }

    public void put(okhttp3.MediaType mediaType, String uploadUrl, String localPath) throws IOException {
        File file = new File(localPath);
        RequestBody body = RequestBody.create(mediaType, file);
        Request request = new Request.Builder()
                .url(uploadUrl)
                .put(body)
                .build();
    }
}
