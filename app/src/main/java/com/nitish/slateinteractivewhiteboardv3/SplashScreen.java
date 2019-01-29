package com.nitish.slateinteractivewhiteboardv3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    android.support.v7.app.ActionBar bar;
    TextView tvone,tvtwo,tvthree,start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bar = getSupportActionBar();
        bar.hide();
        setContentView(R.layout.activity_splash_screen);
        final ImageView splash= findViewById(R.id.splash);
        tvone = findViewById(R.id.textView3);
        tvtwo = findViewById(R.id.centreArrow);
        tvthree = findViewById(R.id.textView2);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300); //You can manage the time of the blink with this parameter
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        tvone.setVisibility(View.INVISIBLE);
        tvtwo.setVisibility(View.INVISIBLE);
        tvthree.setVisibility(View.INVISIBLE);

        start = findViewById(R.id.textView4);
        start.startAnimation(anim);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }, 0);
            }
        });

        /*Animation anim2 = new AlphaAnimation(0.0f, 1.0f);
        anim2.setDuration(400); //You can manage the time of the blink with this parameter
        anim2.setStartOffset(400);
        anim2.setRepeatMode(Animation.REVERSE);
        anim2.setRepeatCount(Animation.INFINITE);

        Animation anim3 = new AlphaAnimation(0.0f, 1.0f);
        anim3.setDuration(200); //You can manage the time of the blink with this parameter
        anim3.setStartOffset(600);
        anim3.setRepeatMode(Animation.REVERSE);
        anim3.setRepeatCount(Animation.INFINITE);

        tvone.startAnimation(anim);
        tvtwo.startAnimation(anim2);
        tvthree.startAnimation(anim3);

        tvone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                    }
                }, 0);
                return false;
            }
        });

        tvtwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }, 0);
                return false;
            }
        });

        tvthree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }, 0);
                return false;
            }
        });

        */


    }

}
