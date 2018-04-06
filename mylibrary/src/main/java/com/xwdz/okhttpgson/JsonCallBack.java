package com.xwdz.okhttpgson;

import com.xwdz.okhttpgson.model.Parser;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public abstract class JsonCallBack<T> implements ICallBack {

    @Override
    public void onNativeResponse(Call call, Response response) throws Exception {
        try {
            final String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.optJSONObject("data");
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> aClass = (Class<T>) type.getActualTypeArguments()[0];
            final Object object = Parser.getInstance().parser(data.toString(), aClass);
            onSuccess(call, (T) object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onSuccess(Call call, T response);
}
