package ct7liang.android.apporitation.OkHttpUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-05-25.
 *
 */

public class COkHttpUtils {

    private static OkHttpClient okHttpClient;
    public static void init(){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
    }

    private boolean isPost;
    private String url;
    private ArrayList<ParamBean> params;
    private ArrayList<HeaderBean> headers;

    public static COkHttpUtils post(){
        if (okHttpClient==null){
            throw new NullPointerException("Ct7OkHttp没有初始化: new COkHttpUtils().init()");
        }
        COkHttpUtils cOkHttpUtils = new COkHttpUtils();
        cOkHttpUtils.isPost = true;
        return cOkHttpUtils;
    }

    public COkHttpUtils url(String string){
        this.url = string;
        return this;
    }

    public COkHttpUtils header(String key, String value){
        if (headers == null){
            headers = new ArrayList<>();
        }
        headers.add(new HeaderBean(key, value));
        return this;
    }

    public COkHttpUtils param(String key, String value){
        if (params == null){
            params = new ArrayList<>();
        }
        params.add(new ParamBean(key, value));
        return this;
    }

    public COkHttpUtils execute(final OnResponse onResponse){
        this.onResponse = onResponse;
        if (isPost){
            Request request = new Request.Builder()
                    .post(getRequestBody())
                    .headers(getHeaders())
                    .url(url)
                    .tag(this)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putSerializable("exception", e);
                    msg.what = 0;
                    msg.setData(b);
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String header = response.header("Set-Cookie");
                    String string = response.body().string();
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putCharSequence("data", string);
                    msg.what = 1;
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
            });
        }
        return this;
    }

    private RequestBody getRequestBody(){
        FormBody.Builder builder = new FormBody.Builder();
        ParamBean paramBean;
        for (int i = 0; i < params.size(); i++) {
            paramBean = params.get(i);
            builder.add(paramBean.key, paramBean.value);
        }
        return builder.build();
    }

    private Headers getHeaders(){
        Headers.Builder builder = new Headers.Builder();
        HeaderBean headerBean;
        for (int i = 0; i < headers.size(); i++) {
            headerBean = headers.get(i);
            builder.add(headerBean.key, headerBean.value);
        }
        return builder.build();
    }

    private OnResponse onResponse;
    public interface OnResponse{
        void onSuccess(String s);
        void onError(Exception e);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    onResponse.onError((Exception) msg.getData().getParcelable("exception"));
                    break;
                case 1:
                    onResponse.onSuccess((String) msg.getData().getCharSequence("data"));
                    break;
            }
        }
    };

}
