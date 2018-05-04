### 添加依赖

```

implementation 'com.xingwei:OkHttpUtil-Json:alpha-v0.3.1'
implementation 'com.squareup.okhttp3:okhttp:3.5.0'

or

complie 'com.xingwei:OkHttpUtil-Json:alpha-v0.3.1'
compile 'com.squareup.okhttp3:okhttp:3.5.0'

```


## 配置OkHttpClient

#### 添加拦截器到默认client


	final Interceptor interceptor = new Interceptor() {
	            @Override
	            public okhttp3.Response intercept(Chain chain) throws IOException {
	                ...
	                return chain.proceed(requestBuilder.build());
	            }
	        };
	
	
	 HttpManager.getInstance().addInterceptor(interceptor)
	                .addNetworkInterceptor();
	                //不要忘记build，不然不会生成client
	                .build();


#### 获取内置OkHttpClient


	HttpManager.getInstance().getDefaultClient();



#### 设置OKHttpClient


	HttpManaget.getInstance().setOkHttpClient();


### 特性

- 支持JSON解析CallBack声明泛型即可
- UI线程回调
- 支持文件下载
- 支持Activity/fragment绑定生命周期
- 支持自定义解析内容
- 如功能不够，可拿到原生Client，自定义功能

## 请求

### Get


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



### POST

**支持JSON泛型解析**


```json
{
	"code": "200",
	"message": "success",
	"data": {
		"id": "1111",
		"username": "xwdz",
		"token": "token"
	}
}
```

Response 定义如下

	public class Response<T> {
	    public String code;
	    public String message;
	    public T data;
	
	    public Response(String code, String message, T data) {
	        this.code = code;
	        this.message = message;
	        this.data = data;
	    }
	}


TestToken定义如下

	public class TestToken  {
	
	    public final String id;
	    public final String username;
	    public final String token;
	
	    public TestToken(String id, String username, String token) {
	        this.id = id;
	        this.username = username;
	        this.token = token;
	    }
	}



请求
	
	 OkHttpRun.post("xxx")
	                .addParams("name", "xwdz")
	                .addParams("age", "13")
	                .execute(new JsonCallBack<Response<TestToken>>() {
	
	                    @Override
	                    public void onSuccess(Call call, Response<TestToken> response) {
	                        mTextView.setText(response.data.toString());
	                    }
	
	
	                    @Override
	                    public void onFailure(Call call, Exception e) {
	                    }
	                });




### 下载文件

	
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











