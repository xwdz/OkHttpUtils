package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.okhttpgson.HttpManager;
import com.xwdz.okhttpgson.LOG;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private static final String GET = "https://api.github.com/users";
    private static final String DOWN = "http://download.kugou.com/download/kugou_android";

    private TextView mTextView;

    private FileCallBack mFileCallBack;

    private long mCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.main);
        HttpManager.getInstance().build();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test";
        mFileCallBack = new FileCallBack(path, "temp.apk", mCurrent) {
            @Override
            protected void onProgressListener(float current, long total) {
                mCurrent = (long) (current * 100);
            }

            @Override
            protected void onFinish(File file) {
                LOG.w("finish");
            }

            @Override
            protected void onStart() {
                LOG.w("start");
            }

            @Override
            protected void onPause() {
                LOG.w("pause");
            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        };
        OkHttpRun.download(DOWN, mCurrent).execute(mFileCallBack);
    }

    public void get(View view) {
        mFileCallBack.pause();
    }

    public void post(View view) {
        OkHttpRun.download(DOWN, mCurrent).execute(mFileCallBack);

//        OkHttpRun.post("")
//                .addParams("name", "xwdz")
//                .addParams("age", "13")
//                .execute(new JsonCallBack<Response<TestToken>>() {
//
//                    @Override
//                    public void onSuccess(Call call, Response<TestToken> response) {
//                        mTextView.setText(response.data.toString());
//                    }
//
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                    }
//                });
    }

    public void start(View view) {
        OtherActivity.start(this);
    }
}
