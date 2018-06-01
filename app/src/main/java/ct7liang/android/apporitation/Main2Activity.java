package ct7liang.android.apporitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ct7liang.android.apporitation.OkHttpUtils.COkHttpUtils;
import okhttp3.OkHttpClient;

public class Main2Activity extends AppCompatActivity {

    private String url = "http://bbs.52bqu.com/biz/loginc/login";  //account password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        OkHttpClient client = new OkHttpClient();

//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });

        COkHttpUtils
                .post().url(url)
                .param("account", "18736607332")
                .param("password", "123456")
                .header("header-key", "header-value")
                .header("header2-key", "header2-value")
                .execute(new COkHttpUtils.OnResponse() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.write("onResponse: " + Thread.currentThread().getId());
                        LogUtils.write("s: " + s);
                    }

                    @Override
                    public void onError(Exception e) {
                        LogUtils.write("e: " + e);
                    }
                });

//        RequestBody requestBody = new FormBody.Builder()
//                .add("account", "18736607332")
//                .add("password", "123456")
//                .build();
//        final Request request = new Request.Builder()
//                .post(requestBody)
//                .header("header-key", "header-value")
//                .addHeader("header2-key", "header2-value")
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.write("onFailure: " + Thread.currentThread().getId() + "___" + e.toString());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String header = response.header("Set-Cookie");
//                String string = response.body().string();
//                LogUtils.write("onResponse: " + Thread.currentThread().getId());
//            }
//        });


//        COkHttpUtils url1 = COkHttpUtils.post();
//        COkHttpUtils url2 = url1.url("000000000000000");
//        Toast.makeText(this, url1.equals(url2)?"TRUE":"FALSE", Toast.LENGTH_SHORT).show();
    }
    
}