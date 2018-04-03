package com.xwdz.okhttpgson;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public abstract class CallBack<T> {

    public void response(Call call, T t){
        onResponse(call,t);
    }

    public void error(Call call, Exception e){
        onError(call,e);
    }

    public void onNativeResponse(Call call, Response response){

    }


    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(Call call, T response);
}
