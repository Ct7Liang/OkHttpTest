package ct7liang.android.apporitation;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ThreadPoolUtils.create();
        ThreadPoolUtils.pool.execute(new ARunnable());


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreadPoolUtils.pool.execute(new CRunnable());
            }
        });
    }


    private class ARunnable implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true){
                SystemClock.sleep(1000);
                i++;
                if (i == 5){
                    break;
                }
                LogUtils.write("-------A_Runnable-------"+Thread.currentThread().getId());
            }
        }
    }

    private class BRunnable implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true){
                SystemClock.sleep(1000);
                i++;
                if (i == 5){
                    break;
                }
                LogUtils.write("-------B_Runnable-------"+Thread.currentThread().getId());
            }
        }
    }

    private class CRunnable implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true){
                SystemClock.sleep(1000);
                LogUtils.write("-------C_Runnable-------"+Thread.currentThread().getId());
            }
        }
    }


}
