package com.xwdz.okhttpgson.impl;

import com.xwdz.okhttpgson.method.Download;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/4/11
 */
public class DownLoadImpl extends GETRequestImpl implements Download {

    private boolean mIsAddRANGE = true;

    public DownLoadImpl(String url, String method, long currentLength) {
        super(url, method);
        if (mIsAddRANGE) {
            addHeaders("RANGE", "bytes=" + currentLength + "-");
        }
    }

    @Override
    public Download setIsAddHeaderRANGE(boolean isAddRANGE) {
        this.mIsAddRANGE = isAddRANGE;
        return this;
    }
}
