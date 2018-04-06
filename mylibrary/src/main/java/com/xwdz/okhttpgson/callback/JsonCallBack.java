package com.xwdz.okhttpgson.callback;

import com.xwdz.okhttpgson.model.Parser;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public abstract class JsonCallBack<T> extends AbstractCallBack<T> implements ICallBack {

    @Override
    protected T parser(Call call, Response response) throws IOException {
        final String json = response.body().string();
        Type type = Parser.getInstance().getSuperclassTypeParameter(getClass());
        final Object object = Parser.getInstance().parser(json, type);
        onSuccess(call, (T) object);
        return (T) object;
    }

    public abstract void onSuccess(Call call, T response);
}
