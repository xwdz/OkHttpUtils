package com.xwdz.okhttpgson;

import com.xwdz.okhttpgson.impl.MethodImpl;
import com.xwdz.okhttpgson.method.OkHttpRequest;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class OkHttpRun {


    public static OkHttpRequest get(String url) {
        return new MethodImpl(url, Method.GET);
    }

    public static OkHttpRequest post(String url) {
        return new MethodImpl(url, Method.POST);
    }

    public static class Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
    }

    public static class MediaType {
        /*
             json : application/json
             xml : application/xml
             png : image/png
             jpg : image/jpeg
             gif : imge/gif
        */
        public static final String IMG = "image/jpeg;charset=utf-8";
        public static final String JSON = "application/json;charset=utf-8";
        public static final String PNG = "image/png;charset=utf-8";

    }
}
