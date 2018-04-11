package com.xwdz.okhttpgson;

import com.xwdz.okhttpgson.impl.DownLoadImpl;
import com.xwdz.okhttpgson.impl.GETRequestImpl;
import com.xwdz.okhttpgson.method.Request;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/31
 */
public class OkHttpRun {


    public static Request get(String url) {
        return new GETRequestImpl(url, Method.GET);
    }

    public static Request post(String url) {
        return new GETRequestImpl(url, Method.POST);
    }

    public static Request download(String url,long current){
        return new DownLoadImpl(url,Method.GET,current);
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
