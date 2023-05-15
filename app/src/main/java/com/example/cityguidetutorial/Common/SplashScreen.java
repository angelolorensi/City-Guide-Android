package com.example.cityguidetutorial.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguidetutorial.R;
import com.example.cityguidetutorial.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 5000;

    //Variables
    ImageView backgroundImage;
    TextView poweredByLine;

    //Animations
    Animation sideAnim,bottomAnim;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Hooks
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.powered_by_line);
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //set Animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBordingScreen", MODE_PRIVATE);

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if (isFirstTime){
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), OnBoarding.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    finish();
                }
            }
        },SPLASH_TIMER);

    }
}