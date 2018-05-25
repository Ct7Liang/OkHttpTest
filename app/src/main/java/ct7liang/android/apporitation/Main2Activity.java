package ct7liang.android.apporitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        LogUtils.write("Main: " + Thread.currentThread().getId());

        String[] sss = {"A", "a", "B", "b"};

        RequestBody requestBody = new FormBody.Builder()
                .add("account", "18736607332")
                .add("password", "123456")
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .header("header", "header")
                .addHeader("addHeader", "addHeader")
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.write("onFailure: " + Thread.currentThread().getId() + "___" + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String header = response.header("Set-Cookie");
                String string = response.body().string();
                LogUtils.write("onResponse: " + Thread.currentThread().getId());
            }
        });
    }
    
}