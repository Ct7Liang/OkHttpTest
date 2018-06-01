package ct7liang.android.apporitation.OkHttpUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;

import ct7liang.android.apporitation.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018-05-25.
 *
 */

public class COkHttpUtils {

    private static String cookieName = "cookie";
    private static OkHttpClient okHttpClient;
    private static SharedPreferences sp;

    /**
     * 初始化方法1, 创建OkHttpClient,避免重复创建, 创建sp文件
     */
    public static void init(Context context){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        sp = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
    }

    /**
     * 初始化方法2, 创建OkHttpClient,避免重复创建, 创建sp文件, 设置cookie的键名
     */
    public static void init(Context context, String cookieKeyName){
        cookieName = cookieKeyName;
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        sp = context.getSharedPreferences("sessionID", Context.MODE_PRIVATE);
    }

    //标记: 是否为POST方法
    private boolean isPost;
    //请求路径
    private String url;
    //参数集
    private ArrayList<ParamBean> params;
    //头参数集
    private ArrayList<HeaderBean> headers;
    //发起请求方
    private Call call;

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
                    .header(cookieName, sp.getString("sessionId", ""))
                    .url(url)
                    .tag(this)
                    .build();
            call = okHttpClient.newCall(request);
            LogUtils.write(System.currentTimeMillis()+"");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.write(System.currentTimeMillis()+"");
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putSerializable("exception", e);
                    msg.what = 0;
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.write(System.currentTimeMillis()+"");
                    String headers = response.header("Set-Cookie");
                    if (headers!=null){
                        sp.edit().putString("sessionId", headers.substring(0, headers.indexOf(';'))).apply();
                    }
                    ResponseBody body = response.body();
                    if (body != null){
                        String string = body.string();
                        Message msg = Message.obtain();
                        Bundle b = new Bundle();
                        b.putCharSequence("data", string);
                        msg.what = 1;
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                }
            });
        }
        return this;
    }

    public void cancel(COkHttpUtils cOkHttpUtils){
        Call call = cOkHttpUtils.call;

    }

    /**
     * 获取请求体 参数配置
     */
    private RequestBody getRequestBody(){
        FormBody.Builder builder = new FormBody.Builder();
        if (params!=null){
            ParamBean paramBean;
            for (int i = 0; i < params.size(); i++) {
                paramBean = params.get(i);
                builder.add(paramBean.key, paramBean.value);
            }
        }
        return builder.build();
    }

    /**
     * 获取请求头 参数配置
     */
    private Headers getHeaders(){
        Headers.Builder builder = new Headers.Builder();
        if (headers!=null){
            HeaderBean headerBean;
            for (int i = 0; i < headers.size(); i++) {
                headerBean = headers.get(i);
                builder.add(headerBean.key, headerBean.value);
            }
        }
        return builder.build();
    }

    private OnResponse onResponse;
    public interface OnResponse{
        void onSuccess(String s);
        void onError(Exception e);
    }

    /**
     * handler切换线程至主线程
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (onResponse!=null){
                switch (msg.what){
                    case 0:
                        onResponse.onError((Exception) msg.getData().getParcelable("exception"));
                        break;
                    case 1:
                        onResponse.onSuccess((String) msg.getData().getCharSequence("data"));
                        break;
                }
            }
        }
    };

}