package com.xwdz.http.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 黄兴伟 (xwdz9989@gamil.com)
 * @since 2018/4/6
 */
public abstract class StringCallBack extends AbstractCallBack<String> {

    @Override
    protected String parser(final Call call, Response response, boolean isMainUIThread) throws IOException {
        final String result = response.body().string();
        post(new Runnable() {
            @Override
            public void run() {
                onSuccess(call, result);
            }
        }, isMainUIThread);
        return result;
    }

    protected abstract void onSuccess(Call call, String response);
}
