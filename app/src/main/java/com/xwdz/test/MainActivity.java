package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xwdz.http.RxOkHttp;
import com.xwdz.http.callback.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://47.106.223.246/";
    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";


    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.log);


        RxOkHttp.get("http://47.106.223.246/test/get")
                .addParams("username", "wxdz")
                .addParams("password", "123456")
                .addHeaders("token", "123123123123")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        mTextView.setText(stringToJSON(response));
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {

                    }
                });


//                .subscribe(new Subscriber<RxResult>() {
//
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//
//                        Log.e("TAG", "start");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.e("TAG", "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        Log.e("TAG", "onError");
//                    }
//
//                    @Override
//                    public void onNext(RxResult rxResult) {
//                        Log.e("TAG", "rxResult:" + rxResult);
//                    }
//                });
//
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<User>() {
//
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        Log.e(RxOkHttp.TAG, "start");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.e(RxOkHttp.TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(RxOkHttp.TAG, "error:" + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(User user) {
//                        Log.e(RxOkHttp.TAG, "onNext:");
//                    }
//                });


//        RxOkHttp.getImpl().get("https://api.github.com/search/users")
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


//        File file = new File(Environment.getExternalStorageDirectory() + "/parser.jpg");

//        mRxOkHttp.get(BASE_URL + "file/query/")
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
//        RxOkHttp.getImpl().post(URL_UPLOAD)
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
            File child = new File(Environment.getExternalStorageDirectory() + "/parser" + i + ".jpg");
            list.add(child);
        }

//        RxOkHttp.postFile(URL_UPLOAD)
//                .uploadFiles("files", list)
//                .addParams("key", "10926a9165054566b6df6a8410e45f08")
//                .addParams("address", "sugar 办公室")
//                .addParams("desc", "测试工具测试")
//                .execute(new StringCallBack() {
//                    @Override
//                    protected void onSuccess(Call call, String response) {
//                        Log.e("TAG", "response:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                        Log.e("TAG", "onFailure:" + e.toString());
//                    }
//                });


    }

    public void stopRequest(View view) {

    }

    public static String stringToJSON(String strJson) {
        // 计数tab的个数
        int tabNum = 0;
        StringBuffer jsonFormat = new StringBuffer();
        int length = strJson.length();

        char last = 0;
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == '}') {
                tabNum--;
                jsonFormat.append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
                jsonFormat.append(c);
            } else if (c == ',') {
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == ':') {
                jsonFormat.append(c + " ");
            } else if (c == '[') {
                tabNum++;
                char next = strJson.charAt(i + 1);
                if (next == ']') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append(c + "\n");
                    jsonFormat.append(getSpaceOrTab(tabNum));
                }
            } else if (c == ']') {
                tabNum--;
                if (last == '[') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append("\n" + getSpaceOrTab(tabNum) + c);
                }
            } else {
                jsonFormat.append(c);
            }
            last = c;
        }
        return jsonFormat.toString();
    }

    private static String getSpaceOrTab(int tabNum) {
        StringBuffer sbTab = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sbTab.append('\t');
        }
        return sbTab.toString();
    }
}
