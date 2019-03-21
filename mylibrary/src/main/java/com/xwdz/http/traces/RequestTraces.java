package com.xwdz.http.traces;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 1. 添加一个正在运行的Http请求
 * 2. 用tag取消一个Http请求
 * 3. 取消所有正在进行Http请求
 *
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/7/20
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class RequestTraces {

    private ArrayMap<String, Call> mRequestMap;


    private static RequestTraces sRequestTraces;

    public synchronized static RequestTraces getImpl() {
        if (sRequestTraces == null) {
            sRequestTraces = new RequestTraces();
        }
        return sRequestTraces;
    }


    private RequestTraces() {
        mRequestMap = new ArrayMap<>();
    }

    public void add(String tag, Call call) {
        if (call != null && !TextUtils.isEmpty(tag)) {
            final Request request = call.request();
            if (request != null) {
                mRequestMap.put(tag, call);
            }
        }
    }

    public void cancel(String tag) {
        Call call = mRequestMap.get(tag);
        if (call != null) {
            if (!call.isCanceled() && call.isExecuted()) {
                call.cancel();
            }
        }
    }

    public void cancelAll() {
        Iterator<ArrayMap.Entry<String, Call>> iterable = mRequestMap.entrySet().iterator();
        while (iterable.hasNext()) {
            Call call = iterable.next().getValue();
            if (call != null) {
                if (!call.isCanceled() && call.isExecuted()) {
                    call.cancel();
                }
            }
        }
    }
}
