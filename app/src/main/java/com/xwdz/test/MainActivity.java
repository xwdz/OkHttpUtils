package com.xwdz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.okhttpgson.CallBack;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.method.MethodGet;
import com.xwdz.okhttpgson.method.MethodPost;

import java.util.LinkedHashMap;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {


    private static final String POST = "https://api.github.com"; //https://api.github.com/users
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);

    }


    public void get(View view) {
        MethodGet methodGet = OkHttpRun.get("");
        methodGet.addParams("user_key", "6a78a77c1ab1416582166e3b02446eea");
        methodGet.addParams(new LinkedHashMap<String, String>());
        methodGet.execute(new CallBack<String>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, String response) {

            }
        });
    }

    public void post(View view) {
        MethodPost post = OkHttpRun.post("");
        post.addParams("username","111");
        post.addParams("password","000000");
        post.setClass(Test2.class);
        post.execute(new CallBack<Test2>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, Test2 response) {

            }
        });
    }
}
