package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.StringCallBack;
import com.xwdz.http.wrapper.PostWrapper;

import java.io.File;
import java.util.HashMap;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://47.106.223.246/";

    private PostWrapper mPostWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(Environment.getExternalStorageDirectory() + "/test.jpg");

//        mQuietHttp.get(BASE_URL + "file/query/")
//                .addParams("id", "123123123123")
//                .tag(BASE_URL + "file/query/123123123123")
//                .run(new StringCallBack() {
//                    @Override
//                    protected void onSuccess(Call call, String response) {
//                        Log.e("TAG", "s:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//
//                    }
//                });

        HashMap<String, File> fileParams = new HashMap<>();
        fileParams.put("file", file);

        HashMap<String, String> textParams = new HashMap<>();
        textParams.put("desc", "Android Test");
        textParams.put("address", "深圳");

        mPostWrapper = QuietHttp.getImpl().post(BASE_URL + "file/upload/")
                .uploadFiles(fileParams, textParams)
                .tag(BASE_URL + "file/upload/" + "testCall");
        mPostWrapper.run(new StringCallBack() {
            @Override
            protected void onSuccess(Call call, String response) {
                Log.e("TAG", "res:" + response);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void stopRequest(View view) {
    }


}
