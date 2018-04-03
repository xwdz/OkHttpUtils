package com.xwdz.okhttpgson;

import android.util.Log;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/3/31/031
 */
public class LOG {

    public static final String TAG = "okHttpRun";

    public static void w(String tag,String value){
        Log.w(tag,value);
    }

    public static void w(String msg){
        Log.w(TAG,msg);
    }
}
