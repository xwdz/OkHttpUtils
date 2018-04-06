package com.xwdz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.githang.android.snippet.security.DigestUtil;
import com.xwdz.okhttpgson.JsonCallBack;
import com.xwdz.okhttpgson.LOG;
import com.xwdz.okhttpgson.OkHttpRun;
import com.xwdz.okhttpgson.method.MethodGet;
import com.xwdz.okhttpgson.method.MethodPost;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String POST = "http://iop.parkingwang.com:9397/iop/auth/login"; //https://api.github.com/users
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpRun.post(POST).addParams("username", "irainiop_hxw")
                .addParams("password", DigestUtil.doDigest("MD5", "000000"))
                .execute(new JsonCallBack<WebToken>() {

                    @Override
                    public void onSuccess(Call call, WebToken response) {
                        LOG.w("response = " + response.toString());
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });
    }


    public void get(View view) {
        MethodGet methodGet = OkHttpRun.get("");
        methodGet.addParams("user_key", "6a78a712331416582166e3b02446eea");
//        methodGet.addParams(new LinkedHashMap<String, String>());
    }

    public void post(View view) {
        MethodPost post = OkHttpRun.post(POST);
        String str = "000000";
        post.addParams("username", "irainiop_hxw");
    }
}
