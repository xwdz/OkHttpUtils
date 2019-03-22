package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.JsonCallBack;
import com.xwdz.http.callback.StringCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://47.106.223.246/";

    private QuietHttp mQuietHttp = QuietHttp.getImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuietHttp.getImpl().get("https://api.github.com/search/users")
                .tag(MainActivity.class.getName())
                .addParams("q", "a")
                .addParams("page", "1")
                .addParams("per_page", "10")
                .execute(new JsonCallBack<Response<List<User>>>() {
                    @Override
                    public void onSuccess(Call call, Response<List<User>> response) {
                        for (User item : response.items) {
                            Log.e("XHttp", "res = " + item.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }
                });


        File file = new File(Environment.getExternalStorageDirectory() + "/test.jpg");

//        mQuietHttp.get(BASE_URL + "file/query/")
//                .addParams("id", "123123123123")
//                .tag(BASE_URL + "file/query/123123123123")
//                .execute(new StringCallBack() {
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


        HashMap fileParams = new HashMap<>();
        fileParams.put("file", file);

        HashMap<String, String> textParams = new HashMap<>();
        textParams.put("desc", "Android Test");
        textParams.put("address", "深圳");

        QuietHttp.getImpl().post(BASE_URL + "file/upload/")
                .uploadFiles(fileParams, textParams)
                .tag(BASE_URL + "file/upload/" + "testCall")
                .setCallbackMainUIThread(false)
                .execute(new StringCallBack() {
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
