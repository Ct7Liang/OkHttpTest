package ct7liang.android.apporitation;

import android.app.Application;
import ct7liang.android.apporitation.OkHttpUtils.COkHttpUtils;

/**
 * Created by Administrator on 2018-05-24.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        COkHttpUtils.init(this, "cookie");
    }

}
