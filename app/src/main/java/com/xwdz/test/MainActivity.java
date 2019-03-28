package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL   = "http://47.106.223.246/";
    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";


    private QuietHttp mQuietHttp = QuietHttp.getImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        QuietHttp.getImpl().get("https://api.github.com/search/users")
//                .tag(MainActivity.class.getName())
//                .params("q", "a")
//                .params("page", "1")
//                .params("per_page", "10")
//                .execute(new JsonCallBack<Response<List<User>>>() {
//                    @Override
//                    public void onSuccess(Call call, Response<List<User>> response) {
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


        File file = new File(Environment.getExternalStorageDirectory() + "/test.jpg");

//        mQuietHttp.get(BASE_URL + "file/query/")
//                .params("id", "123123123123")
//                .tag(BASE_URL + "file/query/123123123123")
//                .execute(new StringCallBack() {
//                    @Override
//                    protected void onSuccess(Call call, String response) {
//                        Log.e("TAG", "s:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//
//                    }
//                });


//        HashMap<String, File> fileParams = new HashMap<>();
//        fileParams.put("file", file);
//
//
//        QuietHttp.getImpl().post(URL_UPLOAD)
//                .addParams("desc", "客户端测试1")
//                .addParams("address", "sugar for shenzhen")
//                .postFile(fileParams)
//                .tag(BASE_URL + "file/upload/" + "testCall")
//                .setCallbackMainUIThread(false)
//                .execute(new StringCallBack() {
//                    @Override
//                    protected void onSuccess(Call call, String response) {
//                        Log.e("TAG", "res:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                        e.printStackTrace();
//                    }
//                });


        List<File> list = new ArrayList<>();


        for (int i = 0; i < 4; i++) {
            File child = new File(Environment.getExternalStorageDirectory() + "/test" + i + ".jpg");
            list.add(child);
        }

        QuietHttp.getImpl()
                .postFile(URL_UPLOAD)
                .uploadFiles("files", list)
                .addParams("key", "10926a9165054566b6df6a8410e45f08")
                .addParams("address", "sugar 办公室")
                .addParams("desc", "测试工具测试")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        Log.e("TAG", "response:" + response);
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                        Log.e("TAG", "onFailure:" + e.toString());
                    }
                });


    }

    public void stopRequest(View view) {

    }
}
