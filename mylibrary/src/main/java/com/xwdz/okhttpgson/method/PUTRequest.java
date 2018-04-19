package com.xwdz.okhttpgson.method;

import okhttp3.RequestBody;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/4/11
 */
public interface PUTRequest extends Request{

     void requestBody(RequestBody body);

     void mediaType(okhttp3.MediaType type);

     void path(String path);
}
