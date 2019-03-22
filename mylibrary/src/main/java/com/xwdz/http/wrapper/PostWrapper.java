package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class PostWrapper extends BaseWrapper<PostWrapper> {

    private static final MediaType IMAGE_TYPE = MediaType.parse("image/png");
    private static final String    NORMAL     = "normal";
    private static final String    UPLOAD     = "upload";

    private LinkedHashMap<String, String> mHeaders = new LinkedHashMap<>();
    private LinkedHashMap<String, String> mParams  = new LinkedHashMap<>();

    private String mFunction = NORMAL;

    private boolean mCallbackToMainUIThread = true;
    private RequestBody mUploadRequestBody;
    private String      mUrl;
    private String      mTag;

    public PostWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient);
        mHeaders.clear();
        mParams.clear();

        mUrl = url;
        mTag = url;
    }


    public PostWrapper uploadFiles(HashMap<String, File> fileParams) {
        uploadFiles(fileParams, null);
        return this;
    }

    public PostWrapper uploadFiles(HashMap<String, File> fileParams, HashMap<String, String> textParams) {
        Assert.checkNull(fileParams, "upload file cannot not null!");

        mFunction = UPLOAD;

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if ((textParams != null && !textParams.isEmpty() && !fileParams.isEmpty())) {
            // 混合参数 以及 文件类型
            builder.setType(MultipartBody.ALTERNATIVE);
        } else {
            // 仅仅上传文件类型
            builder.setType(MultipartBody.FORM);
        }

        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue().getName(),
                    RequestBody.create(IMAGE_TYPE, entry.getValue()));
        }

        for (Map.Entry<String, String> entry : textParams.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        mUploadRequestBody = builder.build();
        return this;
    }

    @Override
    protected Request buildRequest() {
        Assert.checkNull(mUrl, "POST 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder params = new FormBody.Builder();

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl);

        if (NORMAL.equals(mFunction)) {
            for (Map.Entry<String, String> map : mParams.entrySet()) {
                params.add(map.getKey(), map.getValue());
            }
            requestBuilder.post(params.build());
        } else {
            requestBuilder.post(mUploadRequestBody);
        }

        requestBuilder
                .tag(mTag);
        return requestBuilder.build();
    }

    @Override
    public PostWrapper tag(Object object) {
        Assert.checkNull(object, "tag not null!");
        mTag = String.valueOf(object);
        return this;
    }

    @Override
    public PostWrapper addHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    @Override
    public PostWrapper addParams(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    @Override
    public PostWrapper addParams(LinkedHashMap<String, String> params) {
        mParams.putAll(params);
        return this;
    }

    @Override
    public PostWrapper addHeader(LinkedHashMap<String, String> header) {
        mHeaders.putAll(header);
        return this;
    }

    @Override
    protected boolean isCallbackMainUIThread() {
        return mCallbackToMainUIThread;
    }

    @Override
    public PostWrapper setCallbackMainUIThread(boolean isCallbackToMainUIThread) {
        mCallbackToMainUIThread = isCallbackToMainUIThread;
        return this;
    }
}
