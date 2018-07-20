package com.xwdz.test;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/7
 */
public class Response<T> {

    public String total_count;
    public String incomplete_results;
    public T items;

    public Response(String total_count, String incomplete_results, T items) {
        this.total_count = total_count;
        this.incomplete_results = incomplete_results;
        this.items = items;
    }
}
