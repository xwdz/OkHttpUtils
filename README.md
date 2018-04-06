### 添加依赖

```

implementation 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.2'
implementation 'com.squareup.okhttp3:okhttp:3.5.0'

or

complie 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.2' 
compile 'com.squareup.okhttp3:okhttp:3.5.0'

```

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


## 解析

### 默认支持Callback如下

- StringCallBack
- JsonCallBack

### 如需要扩展实现ICallBack即可

```
public interface ICallBack {

    void onFailure(Call call, Exception e);

    void onNativeResponse(Call call, Response response) throws Exception;
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

### Alpha-v0.0.3 修改 minSdkVersion 为15'

### Alpha-v0.0.2 修改groupId为 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.2'

### Alpha-v0.0.1 完成基础功能






