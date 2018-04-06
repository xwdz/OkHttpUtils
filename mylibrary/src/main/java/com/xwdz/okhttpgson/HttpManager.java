package com.xwdz.okhttpgson;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/3/27
 */
public class HttpManager<T> {

    private static final int CONNECT_TIMEOUT_SECONDS = 30;
    private static final int READ_TIMEOUT_SECONDS = 30;
    private static final int WRITE_TIMEOUT_SECONDS = 30;


    private final ArrayList<Call> mCalls = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private static HttpManager sHttpManager;
    private OkHttpClient mClient;
    private OkHttpClient.Builder mBuilder;


    public static HttpManager getInstance() {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                }
            }
        }
        return sHttpManager;
    }

    private HttpManager() {
        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        //默认log拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mBuilder.addInterceptor(logInterceptor);

        mClient = mBuilder.build();
    }

    public void addInterceptor(Interceptor interceptor) {
        mBuilder.addInterceptor(interceptor);
    }

    public void addNetworkInterceptor(Interceptor interceptor) {
        mBuilder.addNetworkInterceptor(interceptor);
    }

    public void setOkHttpClient(OkHttpClient client) {
        this.mClient = client;
    }

    public OkHttpClient getDefaultClient() {
        return mClient;
    }

    public Response execute(final Request request) throws IOException {
        Call call = mClient.newCall(request);
        mCalls.add(call);
        return call.execute();
    }


    public void execute(final Request request, final CallBack<T> callBack) {
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (callBack != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!response.isSuccessful()) {
                                IOException ioException = new IOException("request failed , reponses code is : " + response.code());
                                callBack.onFailure(call, ioException);
                                return;
                            }

                            if (call.isCanceled()) {
                                callBack.onFailure(call, new IOException("Canceled!"));
                                return;
                            }

                            try {
                                callBack.onNativeResponse(call, response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        mCalls.add(call);
    }

    public void cancel() {
        if (!mCalls.isEmpty()) {
            for (Call call : mCalls) {
                if (call.isExecuted()) {
                    call.cancel();
                }
            }
        }
    }
}
