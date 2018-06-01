package ct7liang.android.apporitation.OkHttpUtils;

/**
 * Created by Administrator on 2018-06-01.
 */

public class Login {

    public static void login(final OnLogin onLogin){
        COkHttpUtils
                .post().url("http://bbs.52bqu.com/biz/loginc/login")
                .param("account", "18736607332")
                .param("password", "123456")
                .execute(new COkHttpUtils.OnResponse() {
                    @Override
                    public void onSuccess(String s) {
                        onLogin.onLoginSuccess(s);
                    }

                    @Override
                    public void onError(Exception e) {
                        onLogin.onLoginError(e);
                    }
                });
    }
    public interface OnLogin{
        void onLoginSuccess(String s);
        void onLoginError(Exception e);
    }

}
