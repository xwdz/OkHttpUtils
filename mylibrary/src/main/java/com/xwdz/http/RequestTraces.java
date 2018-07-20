package com.xwdz.http;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Request;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/7/20
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class RequestTraces {

    private ArrayMap<String, Call> mRequestMap;

    public RequestTraces() {
        mRequestMap = new ArrayMap<>();
    }

    public void add(Call call) {
        if (call != null) {
            final Request request = call.request();
            if (request != null) {
                mRequestMap.put((String) request.tag(), call);
            }
        }
    }

    public void cancel(String url) {
        Call call = mRequestMap.get(url);
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
