package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/21
 */
public class PostFileWrapper extends BaseWrapper<PostFileWrapper> {

    private static final MediaType STREAM_TYPE = MediaType.parse("application/octet-stream");

    private LinkedHashMap<String, String> mHeaders = new LinkedHashMap<>();
    private LinkedHashMap<String, String> mParams  = new LinkedHashMap<>();

    private boolean mCallbackToMainUIThread = true;
    private RequestBody mUploadRequestBody;
    private String      mUrl;
    private String      mTag;

    private Map<String, List<File>> mFiles = new HashMap<>();
    private MediaType mMediaType;

    public PostFileWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient);
        mHeaders.clear();
        mParams.clear();
        mFiles.clear();

        mUrl = url;
        mTag = url;
    }


    public PostFileWrapper uploadFiles(String paramName, File file) {
        Assert.checkNull(file, "upload file cannot not null!");

        final ArrayList<File> files = new ArrayList<>();
        files.add(file);
        mFiles.put(paramName, files);

        return this;
    }

    public PostFileWrapper uploadFiles(String paramName, List<File> files) {
        Assert.checkNull(files, "upload file cannot not null!");

        mFiles.put(paramName, files);

        return this;
    }

    public PostFileWrapper uploadFiles(MediaType mediaType, String paramName, List<File> files) {
        Assert.checkNull(files, "upload file cannot not null!");

        mMediaType = mediaType;
        mFiles.put(paramName, files);

        return this;
    }

    private MultipartBody buildBody(MediaType mediaType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if ((mParams != null && !mParams.isEmpty() && !mFiles.isEmpty())) {
            // 混合参数 以及 文件类型
            builder.setType(MultipartBody.ALTERNATIVE);
        } else {
            // 仅仅上传文件类型
            builder.setType(MultipartBody.FORM);
        }


        for (Map.Entry<String, List<File>> entry : mFiles.entrySet()) {
            for (File file : entry.getValue()) {
                builder.addFormDataPart(entry.getKey()
                        , file.getName()
                        , RequestBody.create(mediaType == null ? STREAM_TYPE : mediaType
                                , file));
            }

        }

        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }


    @Override
    protected void ready() {
        mUploadRequestBody = buildBody(mMediaType);
    }

    @Override
    protected Request buildRequest() {
        Assert.checkNull(mUrl, "POST 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl);
        requestBuilder.post(mUploadRequestBody);
        requestBuilder.tag(mTag);
        return requestBuilder.build();
    }

    @Override
    public PostFileWrapper tag(Object object) {
        Assert.checkNull(object, "tag not null!");
        mTag = String.valueOf(object);
        return this;
    }

    @Override
    public PostFileWrapper addHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    @Override
    public PostFileWrapper addParams(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    @Override
    public PostFileWrapper params(LinkedHashMap<String, String> params) {
        mParams.putAll(params);
        return this;
    }

    @Override
    public PostFileWrapper headers(LinkedHashMap<String, String> header) {
        mHeaders.putAll(header);
        return this;
    }


    @Override
    protected boolean isCallbackMainUIThread() {
        return mCallbackToMainUIThread;
    }

    @Override
    public PostFileWrapper setCallbackMainUIThread(boolean isCallbackToMainUIThread) {
        mCallbackToMainUIThread = isCallbackToMainUIThread;
        return this;
    }
}
