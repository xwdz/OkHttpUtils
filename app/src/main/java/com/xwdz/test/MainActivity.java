package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.okhttpgson.HttpManager;
import com.xwdz.okhttpgson.LOG;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.callback.FileCallBack;
import com.xwdz.okhttpgson.callback.JsonCallBack;
import com.xwdz.okhttpgson.callback.StringCallBack;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {


    private static final String GET = "https://api.github.com/users";
    private static final String DOWN = "http://download.kugou.com/download/kugou_android";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                final Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.url(request.url()
                        .newBuilder()
                        .addQueryParameter("c", "1")
                        .addQueryParameter("v", "2")
                        .build());
                return chain.proceed(requestBuilder.build());
            }
        };
        HttpManager.getInstance().addInterceptor(interceptor).build();

        mTextView = findViewById(R.id.main);

        OkHttpRun.get("http://119.29.16.234:7091" + "/v1/auth/getAllUserInfo")
                .addParams("user_key","6a78a77c1ab1416582166e3b02446eea")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });

    }

    public void get(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test";
        OkHttpRun.get(DOWN)
                .execute(new FileCallBack<File>(path, "temp.apk") {
                    @Override
                    protected void onProgressListener(float current, long total) {
                        LOG.w("TAG", "current " + current * 100 + "/" + " " + total);
                    }

                    @Override
                    protected void onFinish(File file) {
                        LOG.w("TAG", "finish");
                    }

                    @Override
                    protected void onStart() {
                        LOG.w("TAG", "start");
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }
                });
    }

    public void post(View view) {
        OkHttpRun.post("")
                .addParams("name", "xwdz")
                .addParams("age", "13")
                .execute(new JsonCallBack<Response<TestToken>>() {

                    @Override
                    public void onSuccess(Call call, Response<TestToken> response) {
                        mTextView.setText(response.data.toString());
                    }


                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });
    }
}
