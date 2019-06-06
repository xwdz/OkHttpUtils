package com.xwdz.http.wrapper;

import com.xwdz.http.utils.Assert;

import java.io.File;
import java.util.ArrayList;
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

    private RequestBody mUploadRequestBody;

    private MediaType mMediaType;

    public PostFileWrapper(OkHttpClient okHttpClient, String url) {
        super(okHttpClient, "POST", url);
    }

    /**
     * 上传文件至服务器
     *
     * @param paramName 服务器参数名称
     * @param file      需要上传文件
     * @return
     */
    public PostFileWrapper uploadFile(String paramName, File file) {
        Assert.checkNull(file, "upload file cannot not null!");

        final ArrayList<File> files = new ArrayList<>();
        files.add(file);
        mUploadFiles.put(paramName, files);

        return this;
    }

    /**
     * 上传多个文件至服务器
     *
     * @param paramName 服务器参数名称
     * @param files     需要上传文件集合
     */
    public PostFileWrapper uploadFile(String paramName, List<File> files) {
        Assert.checkNull(files, "upload file cannot not null!");

        mUploadFiles.put(paramName, files);

        return this;
    }


    public PostFileWrapper uploadFile(MediaType mediaType, String paramName, File file) {
        Assert.checkNull(file, "upload file cannot not null!");
        final ArrayList<File> list = new ArrayList<>();
        list.add(file);

        mMediaType = mediaType;
        mUploadFiles.put(paramName, list);

        return this;
    }

    public PostFileWrapper uploadFile(MediaType mediaType, String paramName, List<File> files) {
        Assert.checkNull(files, "upload file cannot not null!");

        mMediaType = mediaType;
        mUploadFiles.put(paramName, files);

        return this;
    }

    private MultipartBody buildBody(MediaType mediaType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if ((mParams != null && !mParams.isEmpty() && !mUploadFiles.isEmpty())) {
            // 混合参数 以及 文件类型
            builder.setType(MultipartBody.ALTERNATIVE);
        } else {
            // 仅仅上传文件类型
            builder.setType(MultipartBody.FORM);
        }


        for (Map.Entry<String, List<File>> entry : mUploadFiles.entrySet()) {
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
    public Request build() {
        mUploadRequestBody = buildBody(mMediaType);
        Assert.checkNull(mUrl, "POST 请求链接不能为空!");

        final Request.Builder requestBuilder = new Request.Builder();

        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            requestBuilder.addHeader(map.getKey(), map.getValue());
        }

        requestBuilder.url(mUrl);
        requestBuilder.post(mUploadRequestBody);
        if (mTag != null) {
            requestBuilder.tag(mTag);
        }
        return requestBuilder.build();
    }
}
