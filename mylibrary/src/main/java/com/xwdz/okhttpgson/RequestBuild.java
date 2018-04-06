package com.xwdz.okhttpgson;

import java.util.LinkedHashMap;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/6
 */
public class RequestBuild {

    private String url;
    private Object tag;
    private LinkedHashMap<String, String> headers;
    private LinkedHashMap<String, String> params;
    private String method;

    public RequestBuild url(String url) {
        this.url = url;
        return this;
    }

    public RequestBuild tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public RequestBuild header(LinkedHashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestBuild header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public RequestBuild params(String key, String value) {
        params.put(key, value);
        return this;
    }

    public RequestBuild params(LinkedHashMap<String, String> params) {
        this.params.clear();
        this.params.putAll(params);
        return this;
    }

    public RequestBuild get() {
        this.method = Method.GET;
        return this;
    }

    public RequestBuild post() {
        this.method = Method.POST;
        return this;
    }


    public static class Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
    }

}
