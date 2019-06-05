package com.xwdz.http;

import java.io.IOException;

/**
 * @author xingwei.huang (xwdz9989@gmail.com)
 * @since v1.0.0
 */
public interface Convert<T, D> {

    /**
     * @param response 需要转换数据
     */
    T convert(D response) throws IOException;
}
