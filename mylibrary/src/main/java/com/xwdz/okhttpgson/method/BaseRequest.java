package com.xwdz.okhttpgson.method;

import com.xwdz.okhttpgson.CallBack;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public interface BaseRequest {

    Response execute() throws IOException;

    void execute(CallBack callBack);

    void cancel();

    void addParams(String key, String value);

    void addParams(LinkedHashMap<String, String> map);

    void setTag(String tag);

    Request getRequest();

}
