package com.xwdz.http.callback;

import com.xwdz.http.model.Parser;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public abstract class JsonCallBack<T> extends AbstractCallBack<T> {

    @Override
    protected T parser(final Call call, Response response, boolean isMainUIThread) throws IOException {
        final String json = response.body().string();
        Type type = Parser.getInstance().getSuperclassTypeParameter(getClass());
        final Object object = Parser.getInstance().parser(json, type);
        post(new Runnable() {
            @Override
            public void run() {
                onSuccess(call, (T) object);
            }
        }, isMainUIThread);
        return (T) object;
    }

    public abstract void onSuccess(Call call, T response);
}
