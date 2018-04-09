package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.method.OkHttpRequest;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class MethodImpl extends BaseImpl implements OkHttpRequest {

    private final String mMethod;

    public MethodImpl(String url, String method) {
        super(url);
        this.mMethod = method;
    }

    @Override
    public Request buildRequest() {
        Request.Builder requestBuilder = new Request.Builder();
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
            requestBuilder.post(params.build());
        }

        requestBuilder
                .url(mUrl)
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
