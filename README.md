### 添加依赖

> compile 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.2'


### Get

```
 MethodGet methodGet = OkHttpRun.get("url");
        methodGet.addParams("key", "6a78a712331416582166e3b02446eea");
        methodGet.addParams(new LinkedHashMap<String, String>());
        methodGet.execute(new CallBack<String>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(Call call, String response) {

            }
        });
```


### POST

```
 MethodPost post = OkHttpRun.post("url");
        post.addParams("username","abc");
        post.addParams("password","000000");
        //设置解析bean
        post.setClass(Test2.class);
        post.execute(new CallBack<Test2>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(Call call, Test2 response) {
                mTextView.setText(response.toString());
            }
        });
```


### 配置OkHttpClient

> 添加拦截器到默认client

```
HttpManager.getInstance().addInterceptor();
HttpManager.getInstance().addNetworkInterceptor();
```

> 获取内置OkHttpClient

```
HttpManager.getInstance().getDefaultClient();
```

### Alpha-v0.0.3 修改 minSdkVersion 为15'

### Alpha-v0.0.2 修改groupId为 'com.xingwei:OkHttpUtil-Json:alpha-v0.0.2'

### Alpha-v0.0.1 完成基础功能






