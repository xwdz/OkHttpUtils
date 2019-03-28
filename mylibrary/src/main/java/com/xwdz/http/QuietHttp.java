package com.xwdz.http;

import com.xwdz.http.log.HttpLog;
import com.xwdz.http.log.HttpLoggingInterceptor;
import com.xwdz.http.traces.RequestTraces;
import com.xwdz.http.utils.Assert;
import com.xwdz.http.wrapper.GetWrapper;
import com.xwdz.http.wrapper.PostFileWrapper;
import com.xwdz.http.wrapper.PostWrapper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class QuietHttp {

    public static final String TAG = QuietHttp.class.getSimpleName();

    private static QuietHttp sQuietHttp;

    public static QuietHttp getImpl() {
        if (sQuietHttp == null) {
            synchronized (QuietHttp.class) {
                if (sQuietHttp == null) {
                    sQuietHttp = new QuietHttp();
                }
            }
        }
        return sQuietHttp;
    }

    private OkHttpClient mOkHttpClient;

    private QuietHttp() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog(TAG));
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        mOkHttpClient = new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .writeTimeout(20, TimeUnit.SECONDS).build();
    }


    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
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
        getWrapper.params(params);
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
        postWrapper.params(params);
        return postWrapper;
    }


    /**
     * 上传文件至服务器
     *
     * @param url 目标链接
     * @return
     */
    public PostFileWrapper postFile(String url) {
        return new PostFileWrapper(mOkHttpClient, url);
    }

    /**
     * 上传文件至服务器
     *
     * @param url       目标链接
     * @param paramName 服务器参数名称
     * @param file      需要上传文件
     * @return
     */
    public PostFileWrapper postFile(String url, String paramName, File file) {
        PostFileWrapper postWrapper = new PostFileWrapper(mOkHttpClient, url);
        return postWrapper.uploadFiles(paramName, file);
    }

    /**
     * 上传多个文件至服务器
     *
     * @param url       目标链接
     * @param paramName 服务器参数名称
     * @param files     需要上传文件集合
     * @return
     */
    public PostFileWrapper postFile(String url, String paramName, List<File> files) {
        PostFileWrapper postWrapper = new PostFileWrapper(mOkHttpClient, url);
        postWrapper.uploadFiles(paramName, files);
        return postWrapper;
    }


    /**
     * 取消一个正在进行的Http请求
     *
     * @param tag 标记
     */
    public void cancel(Object tag) {
        Assert.checkNull(tag, "tag cannot not null!");

        RequestTraces.getImpl().cancel(tag);
    }

    /**
     * 取消所有正在进行的Http请求
     */
    public void cancellAll() {
        RequestTraces.getImpl().cancelAll();
    }
}
