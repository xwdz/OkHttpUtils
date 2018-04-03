package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.HttpManager;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/4/2/002
 */
public abstract class BaseImpl {

    private HttpManager mHttpManager;
    private Class mClass;

    protected BaseImpl() {
        mHttpManager = HttpManager.getInstance();
    }

    protected Response execute(Request request) throws IOException {
        return mHttpManager.execute(request);
    }

    protected void execute(Request request, CallBack callBack) {
        try {
            this.mHttpManager.execute(request, callBack, mClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cancel() {
        try {
            this.mHttpManager.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClass(Class aClass) {
        this.mClass = aClass;
    }
}
