package com.xwdz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xwdz.http.QuietOkHttp;
import com.xwdz.http.callback.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";

    private TextView mTextView;
    private FrameLayout mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.log);
        mLoading = findViewById(R.id.loading);
    }

    public void requestGet(View view) {
        mLoading.setVisibility(View.VISIBLE);
        QuietOkHttp.get("http://47.106.223.246/test/get")
                .addParams("username", "wxdz")
                .addParams("password", "123456")
                .addHeaders("token", "11111")
                .addHeaders("method", "get")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        mTextView.setText("Get:\n" + stringToJSON(response));
                        mLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                        mLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void requestPost(View view) {
        mLoading.setVisibility(View.VISIBLE);
        QuietOkHttp.post("http://47.106.223.246/test/post")
                .addParams("username", "wxdz")
                .addParams("password", "123456")
                .addHeaders("token", "22222")
                .addHeaders("method", "post")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        mTextView.setText("Post:\n" + stringToJSON(response));
                        mLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                        mLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void requestPostFile(View view) {
        // 确认图片路径
        mLoading.setVisibility(View.VISIBLE);
        List<File> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            File child = new File(Environment.getExternalStorageDirectory() + "/parser" + i + ".jpg");
            list.add(child);
        }

        QuietOkHttp.postFile(URL_UPLOAD)
                .uploadFile("files", list)
                .addParams("key", "10926a9165054566b6df6a8410e45f08")
                .addParams("address", "sugar 办公室")
                .addParams("desc", "测试工具测试")
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        mTextView.setText("postFile:\n" + stringToJSON(response));
                        mLoading.setVisibility(View.GONE);
                        Log.e("TAG", "response:" + response);
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                        Log.e("TAG", "onFailure:" + e.toString());
                        mTextView.setText("error:" + e.toString());
                        mLoading.setVisibility(View.GONE);
                    }
                });
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
