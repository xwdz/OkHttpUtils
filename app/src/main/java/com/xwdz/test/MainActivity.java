package com.xwdz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.okhttpgson.JsonCallBack;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.StringCallBack;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);

    }


    public void get(View view) {
        OkHttpRun.get("")
                .execute(new StringCallBack() {
                    @Override
                    public void onSuccess(Call call, String response) {
                        mTextView.setText(response);
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }
                });
    }

    public void post(View view) {
        OkHttpRun.post("").addParams("name", "11")
                .addParams("pwd", "123")
                .execute(new JsonCallBack<WebToken>() {

                    @Override
                    public void onSuccess(Call call, WebToken response) {
                        mTextView.setText(response.toString());
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });
    }
}
