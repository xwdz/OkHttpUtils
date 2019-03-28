


### 特性

- 支持自动解析JSON返回实体类
- 可随时取消某个请求或者全部请求
- 支持上传多文件
- 支持上传混合参数(文件参数以及json参数)
- 线程默认回调再主线程(`.setCallbackMainUIThread(true /* 默认为true */)`)
- 支持文件下载


$lastVersion = [![](https://jitpack.io/v/xwdz/OkHttpUtils.svg)](https://jitpack.io/#xwdz/OkHttpUtils)


### 添加依赖

```

compile 'com.xwdz:okHttpUtils:$lastVersion'
compile 'com.squareup.okhttp3:okhttp:3.5.0'

```


### 注意


>  一定要保证`.execute(new xxxCallback)`方法最后调用


### 提供两种获取QuietHttp方法

```
1. 直接实例化使用内置配置

    QuietHttp quietHttp = QuietHttp.getImpl();

默认配置如下:

    private QuietHttp() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog("XHttp"));
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .writeTimeout(20, TimeUnit.SECONDS).build();
    }
    
    
2.  传入自定义OkHttpClient

QuietHttp.getImpl().setOkHttpClient(OkHttpClient okHttpClient);

```



## 请求

#### Get
       
	 QuietHttp.getImpl().get("https://api.github.com/search/users")
         .tag(MainActivity.class.getName())
         .addParams("q", "a")
         .addParams("page", "1")
         .addParams("per_page", "10")
         // 回调在子线程
         .setCallbackMainUIThread(false)
         .execute(new JsonCallBack<Response<List<User>>>() {
             @Override
             public void onSuccess(Call call, Response<List<User>> response) {
                 for (User item : response.items) {
                     Log.e("XHttp", "res = " + item.toString());
                 }
             }

             @Override
             public void onFailure(Call call, Exception e) {

             }
         });


#### POST
	
	 QuietHttp.getImpl()
	     .post(URL)
         .tag(MainActivity.class.getName())
         .addParams("q", "xwdz")
         .addParams("page", "1")
         .addParams("per_page", "10")
         .execute(new JsonCallBack<Response<List<User>>>() {
             @Override
             public void onSuccess(Call call, Response<List<User>> response) {
                 for (User item : response.items) {
                     Log.e("XHttp", "res = " + item.toString());
                 }
             }

             @Override
             public void onFailure(Call call, Exception e) {

             }
         });
                         
#### POST 文件

```
        List<File> list = new ArrayList<>();
        list.add("file", file);
    
        QuietHttp.getImpl().postFile(URL)
                .uploadFiles("files",list)
                .tag(mTag)
                .execute(new StringCallBack() {
                    @Override
                    protected void onSuccess(Call call, String response) {
                        Log.e("TAG", "res:" + response);
                    }
    
                    @Override
                    public void onFailure(Call call, Exception e) {
                        e.printStackTrace();
                    }
                });

```
    
                
#### POST文件 + 混合参数

```

         QuietHttp.getImpl()
                .postFile(URL_UPLOAD)
                .uploadFiles("files", list)
                .addParams("key","10926a9165054566b6df6a8410e45f08")
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

```

### 取消一个请求

```

QuietHttp.getImpl().cancel(tag)

```


##### 取消全部请求

```

QuietHttp.getImpl().cancelAll()

```


### 默认支持Callback如下

- StringCallBack
- JsonCallBack
- FileCallBack

### 如需要其他解析扩展，继承AbstractCallBack 即可

**比如**


	public abstract class StringCallBack extends AbstractCallBack<String> {
	
	    @Override
	    protected String parser(Call call, Response response) throws IOException {
	        final String result = response.body().string();
	        onSuccess(call, result);
	        return result;
	    }
	
	    protected abstract void onSuccess(Call call, String response);
	}


### 混淆

```

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okhttpUtils
-dontwarn com.xwdz.http.**
-keep class com.xwdz.http.**{*;}

# gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

```









