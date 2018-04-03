package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.HttpManager;
import com.xwdz.okhttpgson.method.MethodPost;

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
public final class MethodPostImpl extends BaseImpl implements MethodPost {

    private final LinkedHashMap<String, String> mLinkedHashMap;

    private String mUrl;
    private HttpManager mHttpManager;
    private String mTag;
    private Request mRequest;


    public MethodPostImpl(String url) {
        this.mUrl = url;
        this.mTag = url;
        this.mHttpManager = HttpManager.getInstance();
        this.mLinkedHashMap = new LinkedHashMap<>();
    }

    @Override
    public Response execute() throws IOException {
        mRequest = buildPostRequest();
        return mHttpManager.execute(mRequest);
    }

    @Override
    public void execute(CallBack callBack) {
        mRequest = buildPostRequest();
        super.execute(mRequest, callBack);
    }

    @Override
    public void cancel() {
        mHttpManager.cancel();
    }

    @Override
    public void addParams(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key == null");
        }
        mLinkedHashMap.put(key, value);
    }


    @Override
    public void addParams(LinkedHashMap<String, String> map) {
        mLinkedHashMap.clear();
        mLinkedHashMap.putAll(map);
    }

    @Override
    public void setTag(String tag) {
        this.mTag = tag;
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

    @Override
    public void setClass(Class aClass) {
        super.setClass(aClass);
    }

    private Request createPostRequest(String url, LinkedHashMap<String, String> httpParams) {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> map : httpParams.entrySet()) {
            params.add(map.getKey(), map.getValue());
        }
        requestBuilder.url(url).post(params.build());
        requestBuilder.tag(mTag);
        return requestBuilder.build();
    }


    private Request buildPostRequest() {
        return createPostRequest(mUrl, mLinkedHashMap);
    }

}
