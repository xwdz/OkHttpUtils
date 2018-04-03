package com.xwdz.okhttpgson;

import com.xwdz.okhttpgson.impl.MethodGetImpl;
import com.xwdz.okhttpgson.impl.MethodPostImpl;
import com.xwdz.okhttpgson.method.MethodGet;
import com.xwdz.okhttpgson.method.MethodPost;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class OkHttpRun {


    public static MethodGet get(String url){
        return new MethodGetImpl(url);
    }

    public static MethodPost post(String url){
        return new MethodPostImpl(url);
    }
}
