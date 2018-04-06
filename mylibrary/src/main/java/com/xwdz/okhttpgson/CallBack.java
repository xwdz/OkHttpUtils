package com.xwdz.okhttpgson;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public interface CallBack {

    void onFailure(Call call, Exception e);

    void onNativeResponse(Call call, Response response) throws Exception;
}
