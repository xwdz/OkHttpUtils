package com.xwdz.test;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/7
 */
public class Response<T> {
    public String code;
    public String message;
    public T data;

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
