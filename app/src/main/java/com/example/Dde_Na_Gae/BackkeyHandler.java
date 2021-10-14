package com.example.Dde_Na_Gae;

import android.app.Activity;
import android.widget.Toast;

public class BackkeyHandler {

    private long backKeyPressedTime = 0;
    private Activity activity;
    private Toast toast;

    public BackkeyHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed(String msg, double time) {
        if (System.currentTimeMillis() > backKeyPressedTime + (time * 1000)) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide(msg);


            return ;
        } else {
            activity.moveTaskToBack(true);						// 태스크를 백그라운드로 이동
            activity.finish();					// 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    private void showGuide(String msg) {
        toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}