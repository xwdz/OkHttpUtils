package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.okhttpgson.LOG;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.callback.FileCallBack;
import com.xwdz.okhttpgson.callback.JsonCallBack;

import java.io.File;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String GET = "https://api.github.com/users";
    private static final String DOWN = "http://download.kugou.com/download/kugou_android";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);

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
                .execute(new JsonCallBack<Response<WebToken>>() {

                    @Override
                    public void onSuccess(Call call, Response<WebToken> response) {
                        mTextView.setText(response.data.toString());
                    }


                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });
    }
}
