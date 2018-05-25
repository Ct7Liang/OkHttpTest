package ct7liang.android.apporitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        final HashMap<String, String> map = new HashMap<>();
        map.put("name", "lee");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        post("http://172.18.1.189:8082/front/test/hi", map);
                    }
                }.start();
            }
        });
    }


    private void get(String urlStr){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuffer sb = new StringBuffer();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                sb.append(reader.readLine());
            }
            tv.setText(sb);
        } catch (Exception e) {
            e.printStackTrace();
            tv.setText("{ \"success\": false,\n" + "\"errorMsg\": \"后台服务器开小差了!\",\n" + "\"result\":{}}");
        }
    }

    private void post(String urlPath, HashMap<String, String> paramsMap){
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.getOutputStream().write(getParams(paramsMap).getBytes());
            final StringBuilder sb = new StringBuilder();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                sb.append(reader.readLine());
            }else{
                LogUtils.write(conn.getResponseCode()+"");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(sb);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText("{ \"success\": false,\n   \"errorMsg\": \"后台服务器开小差了!\",\n     \"result\":{}}");
                }
            });
        }
    }

    private static String getParams(HashMap<String, String> paramsMap) {
        String result = "";
        for (HashMap.Entry<String, String> entity : paramsMap.entrySet()) {
            result += "&" + entity.getKey() + "=" + entity.getValue();
        }
        return result.substring(1);
    }
}
