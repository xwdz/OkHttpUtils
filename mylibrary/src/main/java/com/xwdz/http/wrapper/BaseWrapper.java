package com.xwdz.http.wrapper;

import android.os.Handler;
import android.os.Looper;

import com.xwdz.http.callback.ICallBack;
import com.xwdz.http.traces.RequestTraces;
import com.xwdz.http.utils.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求基类，处理一些公共逻辑
 *
 * @author xingwei.huang (xwdz9989@gamil.callback)
 * @since 2019/3/21
 */
public abstract class BaseWrapper<T> {

    private static final Handler MAIN_UI_THREAD = new Handler(Looper.getMainLooper());

    private RequestTraces mRequestTraces;
    private OkHttpClient mOkHttpClient;

    //
    public String mUrl;
    protected Object mTag;
    public Map<String, String> mHeaders = new HashMap<>();
    public Map<String, String> mParams = new HashMap<>();
    protected Map<String, List<File>> mUploadFiles = new HashMap<>();
    protected volatile boolean isCallbackToMainUIThread = true;
    protected String mMethod;


    BaseWrapper(OkHttpClient okHttpClient, String method, String url) {
        Assert.checkNull(okHttpClient, "OkHttpClient cannot not null!");

        mMethod = method;
        mUrl = url;
        mRequestTraces = RequestTraces.getImpl();
        mOkHttpClient = okHttpClient;
    }


    public BaseWrapper setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public BaseWrapper addHeaders(Map<String, String> header) {
        mHeaders = header;
        return this;
    }

    public BaseWrapper addParams(Map<String, String> params) {
        mParams = params;
        return this;
    }

    public BaseWrapper addHeaders(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    public BaseWrapper addParams(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    public BaseWrapper setCallbackToMainUIThread(boolean callbackToMainUIThread) {
        isCallbackToMainUIThread = callbackToMainUIThread;
        return this;
    }

    protected abstract Request build();

    public Response execute() throws Throwable {
        final Request request = build();
        Call call = mOkHttpClient.newCall(request);
        mRequestTraces.add(request.tag(), call);
        return call.execute();
    }

    public void execute(final ICallBack iCallBack) {
        final Request request = build();
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
            public void onResponse(final Call call, final Response response) throws IOException {
                if (iCallBack != null) {
                    try {
                        iCallBack.onNativeResponse(call, response, isCallbackToMainUIThread);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mRequestTraces.add(request.tag(), call);
    }
}
