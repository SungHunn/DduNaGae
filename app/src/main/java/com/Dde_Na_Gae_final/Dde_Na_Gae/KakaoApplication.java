package com.Dde_Na_Gae_final.Dde_Na_Gae;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;


public class KakaoApplication extends Application {
    private static KakaoApplication self;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        self = this;
        KakaoSDK.init(new KakaoAdapter() {
            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return self;
                    }
                };
            }
        });

    }
}
