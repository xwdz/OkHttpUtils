package com.xwdz.okhttpgson.method;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/7
 */
public interface MethodPut extends OkHttpRequest {

    void requestBody(RequestBody body);

    void mediaType(MediaType type);

    void path(String path);
}
