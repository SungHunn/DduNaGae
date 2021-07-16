package com.example.Dde_Na_Gae;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "b214210d275c3c68a2ab5a59099d8b41");
    }
}
