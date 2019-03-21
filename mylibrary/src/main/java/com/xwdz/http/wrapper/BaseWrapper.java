package com.xwdz.http.wrapper;

import android.os.Handler;
import android.os.Looper;

import com.xwdz.http.RequestTraces;
import com.xwdz.http.callback.ICallBack;
import com.xwdz.http.listener.WrapperTask;
import com.xwdz.http.utils.Assert;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求基类，处理一些公共逻辑
 *
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public abstract class BaseWrapper<T> implements WrapperTask {

    private static final Handler MAIN_UI_THREAD = new Handler(Looper.getMainLooper());

    protected static final String GET  = "GET";
    protected static final String POST = "POST";

    private RequestTraces mRequestTraces;
    private OkHttpClient  mOkHttpClient;

    public BaseWrapper(OkHttpClient okHttpClient) {
        Assert.checkNull(mOkHttpClient, "OkHttpClient cannot not null!");

        mRequestTraces = new RequestTraces();
        mOkHttpClient = okHttpClient;
    }


    protected abstract Request buildRequest();

    public abstract T tag(Object object);

    public abstract T addHeader(String key, String value);

    public abstract T addParams(String key, String value);

    public abstract T addParams(LinkedHashMap<String, String> params);

    public abstract T addHeader(LinkedHashMap<String, String> header);

    public abstract boolean isCallbackMainUIThread();


    @Override
    public Response run() throws Throwable {
        final Request request = buildRequest();
        Call call = mOkHttpClient.newCall(buildRequest());
        mRequestTraces.add(String.valueOf(request.tag()), call);
        return call.execute();
    }

    @Override
    public void run(final ICallBack iCallBack) {
        final Request request = buildRequest();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (iCallBack != null) {
                    MAIN_UI_THREAD.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallBack.onFailure(call, e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (iCallBack != null) {
                    try {
                        iCallBack.onNativeResponse(call, response, isCallbackMainUIThread());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mRequestTraces.add(String.valueOf(request.tag()), call);
    }

    @Override
    public void cancel(String tag) {
        Assert.checkNull(tag, "tag 不能为空!");

        mRequestTraces.cancel(tag);
    }

    @Override
    public void cancelAll() {
        mRequestTraces.cancelAll();
    }
}
