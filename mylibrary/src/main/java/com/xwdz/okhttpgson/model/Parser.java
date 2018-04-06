package com.xwdz.okhttpgson.model;

import com.google.gson.Gson;

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


    public <T> T parser(String json, Class<T> tClass) {
        return mGson.fromJson(json, tClass);
    }
}
