package ct7liang.android.apporitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ct7liang.android.apporitation.OkHttpUtils.Login;
import ct7liang.android.apporitation.OkHttpUtils.Query;

public class Main2Activity extends AppCompatActivity implements Login.OnLogin, Query.OnQuery {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Login.login(this);

    }

    @Override
    public void onLoginSuccess(String s) {
        LogUtils.write(s);
        Query.query(this);
    }

    @Override
    public void onLoginError(Exception e) {
        LogUtils.write(e.toString());
    }


    @Override
    public void onQuerySuccess(String s) {
        LogUtils.write(s);
    }

    @Override
    public void onQueryError(Exception e) {
        LogUtils.write(e.toString());
    }
}