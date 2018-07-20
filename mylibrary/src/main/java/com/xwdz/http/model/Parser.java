package com.xwdz.http.model;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huangxingwei(xwdz9989 @ gmail.com)
 * @since 2018/4/2
 */
public class Parser {

    private Gson mGson;

    private static Parser sParser;

    private Parser() {
        mGson = new Gson();
    }

    public static Parser getInstance() {
        if (sParser == null) {
            synchronized (Parser.class) {
                if (sParser == null) {
                    sParser = new Parser();
                }
            }
        }
        return sParser;
    }


    public <T> T parser(String json, Type clazz) {
        return mGson.fromJson(json, clazz);
    }

    public Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
    }
}
