package com.xwdz.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xwdz.http.OkHttpManager;
import com.xwdz.http.callback.FileCallBack;
import com.xwdz.http.callback.JsonCallBack;

import java.util.List;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private static final String GET = "https://api.github.com/users";
    private static final String DOWN = "http://download.kugou.com/download/kugou_android";

    private TextView mTextView;

    private FileCallBack mFileCallBack;

    private long mCurrent;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.main);
        mProgressBar = findViewById(R.id.progressBar);

        OkHttpManager okHttpManager = new OkHttpManager();
        okHttpManager.get("https://api.github.com/search/users")
                .addParams("q", "a")
                .addParams("page", "1")
                .addParams("per_page", "10")
                .callbackMainUIThread(true)
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


//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test";
//        File file = new File(path);
//        mFileCallBack = new FileCallBack(file) {
//
//            @Override
//            public void onFailure(okhttp3.Call call, Exception e) {
//
//            }
//
//            @Override
//            protected void onProgressListener(int percent, long currentLength, long total) {
//
//            }
//
//            @Override
//            protected void onFinish(File file) {
//                LOG.w("finish");
//            }
//
//            @Override
//            protected void onStart() {
//                LOG.w("start");
//            }
//
//        };
    }

    public void get(View view) {
    }

    public void post(View view) {


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
