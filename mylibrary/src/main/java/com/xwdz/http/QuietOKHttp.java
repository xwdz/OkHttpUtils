package com.xwdz.http;

import com.xwdz.http.log.HttpLog;
import com.xwdz.http.log.HttpLoggingInterceptor;
import com.xwdz.http.wrapper.GetWrapper;
import com.xwdz.http.wrapper.PostWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class QuietOKHttp {

    public static final String TAG = QuietHttpUtils.class.getSimpleName();

    public static class Builder {

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();

        public QuietOKHttp.Builder addInterceptor(Interceptor interceptor) {
            mBuilder.addInterceptor(interceptor);
            return this;
        }

        public QuietOKHttp.Builder connectTimeout(long time, TimeUnit timeUnit) {
            mBuilder.connectTimeout(time, timeUnit);
            return this;
        }

        public QuietOKHttp.Builder readTimeout(long readTimeout, TimeUnit timeUnit) {
            mBuilder.readTimeout(readTimeout, timeUnit);
            return this;
        }

        public QuietOKHttp.Builder writeTimeout(long writeTimeout, TimeUnit timeUnit) {
            mBuilder.writeTimeout(writeTimeout, timeUnit);
            return this;
        }

        public QuietOKHttp.Builder addNetworkInterceptor(Interceptor interceptor) {
            mBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        public QuietOKHttp.Builder newBuilder(OkHttpClient.Builder builder) {
            mBuilder = builder;
            return this;
        }

        public QuietOKHttp build() {
            return new QuietOKHttp(mBuilder);
        }
    }

    private static QuietOKHttp sQuietOkHttp;


    public static QuietOKHttp getInstance() {
        return getInstance(null);
    }

    public static QuietOKHttp getInstance(OkHttpClient.Builder builder) {
        if (sQuietOkHttp == null) {
            synchronized (QuietHttpUtils.class) {
                if (sQuietOkHttp == null) {
                    sQuietOkHttp = new QuietOKHttp(builder);
                }
            }
        }
        return sQuietOkHttp;
    }

    private OkHttpClient mOkHttpClient;


    private QuietOKHttp(OkHttpClient.Builder builder) {
        if (builder == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog(TAG));
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            mOkHttpClient = new OkHttpClient.Builder()
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(logInterceptor)
                    .writeTimeout(20, TimeUnit.SECONDS).build();
        }
    }

    /**
     * 发起一个Http Get 请求
     *
     * @param url 目标链接
     * @return
     */
    public GetWrapper get(String url) {
        return new GetWrapper(mOkHttpClient, url);
    }

    public GetWrapper get(String url, String key, String value) {
        GetWrapper getWrapper = new GetWrapper(mOkHttpClient, url);
        getWrapper.addParams(key, value);
        return getWrapper;
    }

    public GetWrapper get(String url, LinkedHashMap<String, String> params) {
        GetWrapper getWrapper = new GetWrapper(mOkHttpClient, url);
        getWrapper.addParams(params);
        return getWrapper;
    }

    /**
     * 发起一个Http Post 请求
     *
     * @param url 目标链接
     * @return
     */
    public PostWrapper post(String url) {
        return new PostWrapper(mOkHttpClient, url);
    }

    public PostWrapper post(String url, String key, String value) {
        PostWrapper postWrapper = new PostWrapper(mOkHttpClient, url);
        postWrapper.addParams(key, value);
        return postWrapper;
    }

    public PostWrapper post(String url, LinkedHashMap<String, String> params) {
        PostWrapper postWrapper = new PostWrapper(mOkHttpClient, url);
        postWrapper.addParams(params);
        return postWrapper;
    }

    /**
     * 上传多个文件至服务器
     *
     * @param url        目标链接
     * @param fileParams 文件参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  文件路径
     * @return
     */
    public PostWrapper uploadFile(String url, HashMap<String, File> fileParams) {
        PostWrapper postWrapper = new PostWrapper(mOkHttpClient, url);
        return postWrapper.uploadFiles(fileParams);
    }

    /**
     * 上传多个文件至服务器
     *
     * @param url        目标链接
     * @param fileParams 文件参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  本地文件路径
     * @param textParams 混合参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  参数值
     * @return
     */
    public PostWrapper uploadFile(String url, HashMap<String, File> fileParams, HashMap<String, String> textParams) {
        PostWrapper postWrapper = new PostWrapper(mOkHttpClient, url);
        postWrapper.uploadFiles(fileParams, textParams);
        return postWrapper;
    }
}