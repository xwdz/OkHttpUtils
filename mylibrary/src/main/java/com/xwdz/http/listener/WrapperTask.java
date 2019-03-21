package com.xwdz.http.listener;

import com.xwdz.http.callback.ICallBack;

import okhttp3.Response;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public interface WrapperTask {

    Response execute() throws Throwable;

    void execute(ICallBack iCallBack);

    void cancel(String tag);

    void cancelAll();
}
