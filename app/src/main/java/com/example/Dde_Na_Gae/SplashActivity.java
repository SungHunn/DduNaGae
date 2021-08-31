package com.example.Dde_Na_Gae;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
//    private FirebaseRemoteConfig mFirebaseRemoteConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Intent intent = new Intent(getApplicationContext(), OpenApi.class);
//        Intent intent_main = new Intent(getApplicationContext(),Mainactivity.class);
        startActivity(intent);

        linearLayout = (LinearLayout)findViewById(R.id.splashactivity_Linearlayout);
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.default_config);
//
//        mFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        if (task.isSuccessful()) {
//                            boolean updated = task.getResult();
//
//
//
//                        } else {
//
//                        }
//                        displayMessage();
//                    }
//                });
//
//
//
//    }
//
//    private void displayMessage() {
//
//        String splash_background = mFirebaseRemoteConfig.getString("splash_background");
//        boolean caps = mFirebaseRemoteConfig.getBoolean("splash_message_caps");
//        String splash_message = mFirebaseRemoteConfig.getString("splash_message");
//
//        linearLayout.setBackgroundColor(Color.parseColor(splash_background));
//
//        if(caps){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(splash_message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//                    finish();
//                }
//            });
//
//            builder.create().show();
//        }else{
//            Intent intent_main = new Intent(getApplicationContext(),Mainactivity.class);
//            startActivity(intent_main);
//        }
    }
}
