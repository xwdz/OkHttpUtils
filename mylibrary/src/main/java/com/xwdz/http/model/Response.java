package com.xwdz.http.model;

/**
 * @author 黄兴伟 (xwdz9989@gamil.com)
 * @since 2018/4/6
 */
public class Response<T> {

    public final String code;
    public final String message;
    public final T data;

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
