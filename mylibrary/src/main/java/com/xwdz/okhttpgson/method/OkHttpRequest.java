package com.xwdz.okhttpgson.method;

import com.xwdz.okhttpgson.ICallBack;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public interface OkHttpRequest {

    Response execute() throws IOException;

    void execute(ICallBack callBack);

    void cancel();

    OkHttpRequest addParams(String key, String value);

    OkHttpRequest addParams(LinkedHashMap<String, String> params);

    OkHttpRequest addHeaders(String key, String value);

    OkHttpRequest addHeaders(LinkedHashMap<String, String> headers);

    OkHttpRequest setTag(String tag);

    Request getRequest();

}
