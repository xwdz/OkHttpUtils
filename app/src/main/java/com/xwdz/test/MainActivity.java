package com.xwdz.test;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private static final String GET = "https://api.github.com/users";
    private static final String DOWN = "http://download.kugou.com/download/kugou_android";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);
        HttpManager.getInstance().build();
    }

    public void get(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test";
        OkHttpRun.get(DOWN)
                .execute(new FileCallBack(path, "temp.apk") {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    protected void onProgressListener(final float current, long total) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(String.valueOf((int) (current * 100)));
                            }
                        });
                    }

                    @Override
                    protected void onFinish(File file) {
                        LOG.w("TAG", "finish " + file.getAbsolutePath());
                        mTextView.setText("0");
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

    public void start(View view){
        OtherActivity.start(this);
    }
}
