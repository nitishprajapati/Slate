package com.nitish.slateinteractivewhiteboardv3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SetupSplashScreen extends AppCompatActivity {
    android.support.v7.app.ActionBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_splash_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bar = getSupportActionBar();
        bar.hide();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SetupSplashScreen.this, SplashScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_1, R.anim.fade_out_1);
                finish();
            }
        }, 200);
    }
}
