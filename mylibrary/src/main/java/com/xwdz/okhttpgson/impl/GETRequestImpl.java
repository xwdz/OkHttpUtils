package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.method.Request;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;

/**
 * @author huangxingwei(xwdz9989 @ gmail.com)
 * @since 2018/3/31
 */
public class GETRequestImpl extends BaseImpl implements Request {

    private final String mMethod;

    public GETRequestImpl(String url, String method) {
        super(url);
        this.mMethod = method;
    }

    @Override
    public okhttp3.Request buildRequest() {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        FormBody.Builder params = new FormBody.Builder();

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        if (OkHttpRun.Method.GET.equals(mMethod)) {
            requestBuilder.url(mUrl + appendHttpParams(mParams));
        } else if (OkHttpRun.Method.POST.equals(mMethod)) {
            for (Map.Entry<String, String> map : mParams.entrySet()) {
                params.add(map.getKey(), map.getValue());
            }
            requestBuilder.url(mUrl);
            requestBuilder.post(params.build());
        }

        requestBuilder
                .tag(mTag);

        return requestBuilder.build();
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
