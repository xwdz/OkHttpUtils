package com.xwdz.okhttpgson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/6
 */
public abstract class StringCallBack implements ICallBack {
    @Override
    public void onNativeResponse(Call call, Response response) throws Exception {
        try {
            final String json = response.body().string();
            onSuccess(call, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onSuccess(Call call, String response);
}
