package com.xwdz.http;

import com.xwdz.http.wrapper.GetWrapper;
import com.xwdz.http.wrapper.PostWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author huangxingwei(xwdz9989@gmail.com)
 * @since 2018/3/27
 */
public class QuietHttpUtils {

    /**
     * 发起一个Http Get 请求
     *
     * @param url 目标链接
     * @return
     */
    public static GetWrapper get(String url) {
        return QuietOKHttp.getInstance().get(url);
    }

    public static GetWrapper get(String url, LinkedHashMap<String, String> params) {
        return QuietOKHttp.getInstance().get(url).addParams(params);
    }

    /**
     * 发起一个Http Post 请求
     *
     * @param url 目标链接
     * @return
     */
    public static PostWrapper post(String url) {
        return QuietOKHttp.getInstance().post(url);
    }

    /**
     * 上传多个文件至服务器
     *
     * @param url        目标链接
     * @param fileParams 文件参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  文件路径
     * @return
     */
    public static PostWrapper uploadFile(String url, HashMap<String, File> fileParams) {
        return QuietOKHttp.getInstance().post(url).uploadFiles(fileParams);
    }

    /**
     * 上传多个文件至服务器
     *
     * @param url        目标链接
     * @param fileParams 文件参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  本地文件路径
     * @param textParams 混合参数
     *                   【key】    ===>  服务器参数名称
     *                   【value】  ===>  参数值
     * @return
     */
    public static PostWrapper uploadFile(String url, HashMap<String, File> fileParams, HashMap<String, String> textParams) {
        return QuietOKHttp.getInstance().post(url).uploadFiles(fileParams, textParams);
    }

}
