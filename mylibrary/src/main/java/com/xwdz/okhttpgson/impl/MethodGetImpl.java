package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.method.MethodGet;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31/031
 */
public class MethodGetImpl extends BaseImpl implements MethodGet {


    public MethodGetImpl(String url) {
        super(url);
    }

    @Override
    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            builder.addHeader(map.getKey(), map.getValue());
        }
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
