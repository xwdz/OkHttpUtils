package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class GetWrapper extends BaseWrapper<GetWrapper> {

    public GetWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient, "GET", url);
    }


    @Override
    protected Request build() {
        Assert.checkNull(mUrl, "GET 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();
        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl + appendHttpParams(mParams));

        if (mTag != null) {
            requestBuilder
                    .tag(mTag);
        }
        return requestBuilder.build();
    }

    private static String appendHttpParams(Map<String, String> sLinkedHashMap) {
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
