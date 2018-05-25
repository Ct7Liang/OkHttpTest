package ct7liang.android.apporitation.OkHttpUtils;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018-05-25.
 */

public class Ct7OkHttp {
    
    private OkHttpClient okHttpClient;
    public void init(){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
    }

    public void POST(){
        if (okHttpClient==null){
            throw new NullPointerException("Ct7OkHttp没有初始化: new Ct7OkHttp().init()");
        }
    }

    public void add(String key, String value){

    }


//    RequestBody requestBody = new FormBody.Builder()
//            .add("account", "18736607332")
//            .add("password", "123456")
//            .build();
//    final Request request = new Request.Builder()
//            .post(requestBody)
//            .header("header", "header")
//            .addHeader("addHeader", "addHeader")
//            .url(url)
//            .build();
//        client.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            LogUtils.write("onFailure: " + Thread.currentThread().getId() + "___" + e.toString());
//        }
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            String header = response.header("Set-Cookie");
//            String string = response.body().string();
//            LogUtils.write("onResponse: " + Thread.currentThread().getId());
//        }
//    });
}
