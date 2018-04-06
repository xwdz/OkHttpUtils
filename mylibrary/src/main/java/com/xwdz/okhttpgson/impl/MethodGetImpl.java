package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.method.MethodGet;
import com.xwdz.okhttpgson.method.OkHttpRequest;

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
public class MethodGetImpl extends BaseImpl implements MethodGet {

    private String mUrl;
    private Object mTag;
    private Request mRequest;


    public MethodGetImpl(String url) {
        super();
        this.mUrl = url;
        this.mTag = url;
    }

    @Override
    public Response execute() throws IOException {
        assertRequest(mRequest);
        return super.execute(mRequest);
    }

    @Override
    public void execute(CallBack callBack) {
        assertRequest(mRequest);
        super.execute(mRequest, callBack);
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
        mHeaders.clear();
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

    @Override
    public OkHttpRequest create() {
        this.mRequest = buildRequest();
        return this;
    }

    private Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl + appendHttpParams(mParams));
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
