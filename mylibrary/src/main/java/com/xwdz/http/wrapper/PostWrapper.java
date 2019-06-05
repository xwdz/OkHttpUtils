package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class PostWrapper extends BaseWrapper<PostWrapper> {

    public PostWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient, "POST", url);
    }


    @Override
    public Request build() {
        Assert.checkNull(mUrl, "POST 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl);

        for (Map.Entry<String, String> map : mParams.entrySet()) {
            params.add(map.getKey(), map.getValue());
        }
        requestBuilder.post(params.build());

        if (mTag != null){
            requestBuilder
                    .tag(mTag);
        }

        return requestBuilder.build();
    }

}
