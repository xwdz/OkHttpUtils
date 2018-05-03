package com.xwdz.okhttpgson.method;

import com.xwdz.okhttpgson.callback.ICallBack;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public interface Request {

    Response execute() throws IOException;

    void execute(ICallBack callBack);

    void cancel();

    Request addParams(String key, String value);

    Request addParams(LinkedHashMap<String, String> params);

    Request addHeaders(String key, String value);

    Request addHeaders(LinkedHashMap<String, String> headers);

    Request setTag(String tag);

    Request setCallBackToMainUIThread(boolean isMainUIThread);

    okhttp3.Request getRequest();

}
