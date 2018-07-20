package com.xwdz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    // private OkHttpManager okHttpManager = new OkHttpManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        okHttpManager.get("https://api.github.com/search/users")
//                .tag(MainActivity.class.getName())
//                .addParams("q", "a")
//                .addParams("page", "1")
//                .callbackMainUIThread(false)
//                .addParams("per_page", "10")
//                .execute(new JsonCallBack<Response<List<User>>>() {
//                    @Override
//                    public void onSuccess(Call call, Response<List<User>> response) {
//                        if (Looper.getMainLooper() == Looper.myLooper()) {
//                            Log.e("TAG", "main thread ");
//                        }
//                        for (User item : response.items) {
//                            Log.e("XHttp", "res = " + item.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//
//                    }
//                });
    }

    public void stopRequest(View view) {
        //okHttpManager.cancel(MainActivity.class.getName());
    }


}
