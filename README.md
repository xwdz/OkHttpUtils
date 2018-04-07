### 添加依赖

```

implementation 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.6'
implementation 'com.squareup.okhttp3:okhttp:3.5.0'

or

complie 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.6' 
compile 'com.squareup.okhttp3:okhttp:3.5.0'

```

### Feature

- 支持JSON解析CallBack声明泛型即可
- UI线程回调
- 支持文件下载
- 支持Activity/fragment绑定生命周期
- 支持自定义解析内容
- 如功能不够，可拿到原生Client，自定义功能

## 请求

### Get

```
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
```


### POST

```
OkHttpRun.post(POST).addParams("name", "xwd")
                .addParams("pwd", "123")
                .execute(new JsonCallBack<Token>() {

                    @Override
                    public void onSuccess(Call call, Token response) {
                        mTextView.setText(response.toString());
                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                    }
                });
```

### 下载文件

```
String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test";
        OkHttpRun.get("http://download.kugou.com/download/kugou_android")
                //path 文件路径
                // 文件名称
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


```


## 解析

### 默认支持Callback如下

- StringCallBack
- JsonCallBack
- FileCallBack

### 如需要其他解析扩展，继承AbstractCallBack 即可

**比如**
```

public abstract class StringCallBack extends AbstractCallBack<String> {

    @Override
    protected String parser(Call call, Response response) throws IOException {
        final String result = response.body().string();
        onSuccess(call, result);
        return result;
    }

    protected abstract void onSuccess(Call call, String response);
}

```


## 配置OkHttpClient

#### 添加拦截器到默认client

```
HttpManager.getInstance().addInterceptor();
HttpManager.getInstance().addNetworkInterceptor();
```

#### 获取内置OkHttpClient

```
HttpManager.getInstance().getDefaultClient();
```


#### 设置OKHttpClient

```
HttpManaget.getInstance().setOkHttpClient();
```








