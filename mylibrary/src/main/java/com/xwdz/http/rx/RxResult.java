package com.xwdz.http.rx;

import java.util.Map;

/**
 * @author xingwei.huang (xwdz9989@gmail.com)
 * @since v1.0.0
 */
public class RxResult {

    private Map<String, String> mHeaders;
    private Throwable mError;
    private byte[] response;

    public RxResult() {

    }

    public RxResult(Map<String, String> headers, Throwable error, byte[] response) {
        this.mHeaders = headers;
        this.mError = error;
        this.response = response;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Throwable getError() {
        return mError;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public void setError(Throwable error) {
        mError = error;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }
}
