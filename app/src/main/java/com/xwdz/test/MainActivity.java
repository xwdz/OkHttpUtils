package com.xwdz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.githang.android.snippet.security.DigestUtil;
import com.xwdz.okhttpgson.StringCallBack;
import com.xwdz.okhttpgson.JsonCallBack;
import com.xwdz.okhttpgson.OkHttpRun;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String POST = "http://iop.parkingwang.com:9397/iop/auth/login"; //
    private static final String GET = "https://api.github.com/users";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.main);

    }

    public void get(View view) {
        OkHttpRun.get(GET)
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
        OkHttpRun.post(POST).addParams("username", "irainiop_hxw")
                .addParams("password", DigestUtil.doDigest("MD5", "000000"))
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
