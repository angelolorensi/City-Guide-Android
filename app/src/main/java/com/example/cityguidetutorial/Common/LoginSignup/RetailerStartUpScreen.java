package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cityguidetutorial.R;
import com.example.cityguidetutorial.User.UserDashboard;

public class RetailerStartUpScreen extends AppCompatActivity {

    Button loginBtn, signUpBtn;
    ImageView goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_start_up_screen);

        //Hooks
        loginBtn = findViewById(R.id.login_btn);
        goBackBtn = findViewById(R.id.start_up_screen_back_btn);
        signUpBtn = findViewById(R.id.signup_btn);

        callSignUpScreen();
        callLoginScreen();
        goBackBtn();

    }

    void callLoginScreen(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }

    void callSignUpScreen(){
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(findViewById(R.id.signup_btn),"transition_signup");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }

    void goBackBtn(){
        goBackBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserDashboard.class)));
    }
}