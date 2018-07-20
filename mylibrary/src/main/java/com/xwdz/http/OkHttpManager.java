package com.xwdz.http;

import android.os.Handler;
import android.text.TextUtils;

import com.xwdz.http.callback.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/27
 */
public class OkHttpManager {


    public static class Builder {

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();

        public Builder addInterceptor(Interceptor interceptor) {
            mBuilder.addInterceptor(interceptor);
            return this;
        }

        public Builder connectTimeout(long time, TimeUnit timeUnit) {
            mBuilder.connectTimeout(time, timeUnit);
            return this;
        }

        public Builder readTimeout(long readTimeout, TimeUnit timeUnit) {
            mBuilder.readTimeout(readTimeout, timeUnit);
            return this;
        }

        public Builder writeTimeout(long writeTimeout, TimeUnit timeUnit) {
            mBuilder.writeTimeout(writeTimeout, timeUnit);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            mBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        public Builder newBuilder(OkHttpClient.Builder builder) {
            mBuilder = builder;
            return this;
        }

        public OkHttpManager build() {
            return new OkHttpManager(mBuilder);
        }
    }

    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final LinkedHashMap<String, String> PARAMS = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> HEADERS = new LinkedHashMap<>();

    private static final ArrayList<Call> CALLS = new ArrayList<>();
    private static final Handler MAIN_UI_THREAD = new Handler();

    private boolean isMainUIThread = true;
    private String mMethod = GET;
    private String mTag;
    private String mUrl;
    private OkHttpClient mOkHttpClient;

    public OkHttpManager(OkHttpClient.Builder builder) {
        mOkHttpClient = builder.build();
    }

    public OkHttpManager() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog("XHttp"));
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .writeTimeout(20, TimeUnit.SECONDS).build();
    }

    public OkHttpManager callbackMainUIThread(boolean isMainUIThread) {
        this.isMainUIThread = isMainUIThread;
        return this;
    }

    public OkHttpManager tag(String tag) {
        mTag = tag;
        return this;
    }

    public OkHttpManager get(String url) {
        mMethod = GET;
        mUrl = url;
        return this;
    }

    public OkHttpManager post(String url) {
        mMethod = POST;
        mUrl = url;
        return this;
    }

    public OkHttpManager addParams(String key, String value) {
        PARAMS.put(key, value);
        return this;
    }

    public OkHttpManager addParams(LinkedHashMap<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public OkHttpManager addHeader(String key, String value) {
        HEADERS.put(key, value);
        return this;
    }

    public OkHttpManager addHeader(LinkedHashMap<String, String> headers) {
        HEADERS.putAll(headers);
        return this;
    }


    public Response execute() throws IOException {
        Call call = mOkHttpClient.newCall(buildRequest());
        CALLS.add(call);
        return call.execute();
    }

    public void execute(final ICallBack iCallBack) {
        Call call = mOkHttpClient.newCall(buildRequest());
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
                        iCallBack.onNativeResponse(call, response, isMainUIThread);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        CALLS.add(call);
    }


    private Request buildRequest() {
        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("url = " + mUrl);
        }

        final Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();

        for (Map.Entry<String, String> map : HEADERS.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        if (GET.equals(mMethod)) {
            requestBuilder.url(mUrl + Utils.appendHttpParams(PARAMS));
        } else if (POST.equals(mMethod)) {
            for (Map.Entry<String, String> map : PARAMS.entrySet()) {
                params.add(map.getKey(), map.getValue());
            }
            requestBuilder.url(mUrl);
            requestBuilder.post(params.build());
        }

        requestBuilder
                .tag(mTag);
        return requestBuilder.build();
    }
}
