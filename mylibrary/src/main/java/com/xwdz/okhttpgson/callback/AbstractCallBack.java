package com.xwdz.okhttpgson.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 黄兴伟 (xwdz9989@gamil.com)
 * @since 2018/4/6
 */
public abstract class AbstractCallBack<T> implements ICallBack {

    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onNativeResponse(Call call, Response response) throws Exception {
        if (!response.isSuccessful()) {
            IOException ioException = new IOException("request failed , reponses code is : " + response.code());
            onFailure(call, ioException);
            return;
        }

        if (call.isCanceled()) {
            onFailure(call, new IOException("Canceled!"));
            return;
        }

        parser(call, response);
    }

    protected abstract T parser(Call call, Response response) throws IOException;
}
