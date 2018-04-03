package com.xwdz.okhttpgson.impl;

import android.text.TextUtils;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.method.MethodGet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31/031
 */
public final class MethodGetImpl extends BaseImpl implements MethodGet {

    private final LinkedHashMap<String, String> mLinkedHashMap;

    private String mUrl;
    private String mTag;
    private Request mRequest;


    public MethodGetImpl(String url) {
        super();
        this.mUrl = url;
        this.mTag = url;
        this.mLinkedHashMap = new LinkedHashMap<>();
    }

    @Override
    public Response execute() throws IOException {
        mRequest  = buildRequest();
        return super.execute(mRequest);
    }

    @Override
    public void execute(CallBack callBack) {
        mRequest  = buildRequest();
        super.execute(mRequest,callBack);
    }

    @Override
    public void cancel() {
        super.cancel();
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
    public void setClass(Class gsonClass) {

    }


    private Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl + appendHttpParams(mLinkedHashMap));
        builder.tag(mTag);
        return builder.build();
    }

    private String appendHttpParams(LinkedHashMap<String, String> sLinkedHashMap) {
        Iterator<String> keys = sLinkedHashMap.keySet().iterator();
        Iterator<String> values = sLinkedHashMap.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < sLinkedHashMap.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != sLinkedHashMap.size() - 1) {
                stringBuffer.append("&");
            }
        }

        return stringBuffer.toString();
    }
}
