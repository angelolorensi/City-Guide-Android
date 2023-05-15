package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguidetutorial.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    ImageView backBtn;
    TextView titleTxt;
    Button nextBtn;
    TextInputLayout fullname, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_signup);

        //Hooks
        nextBtn = findViewById(R.id.signup_next_btn);
        backBtn = findViewById(R.id.signup_back_btn);
        titleTxt = findViewById(R.id.signup_title_text);
        fullname = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

        //Button Methods
        goBackBtn();
        callNextSignupScreen();

    }

    public void callNextSignupScreen() {
        nextBtn.setOnClickListener(view -> {

            if(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
                return;
            }

            String fullnameS = fullname.getEditText().getText().toString().trim();
            String usernameS = username.getEditText().getText().toString().trim();
            String emailS = email.getEditText().getText().toString().trim();
            String passwordS = password.getEditText().getText().toString().trim();

            Intent intent = new Intent(getApplicationContext(), Signup2ndClass.class);
            intent.putExtra("fullname", fullnameS);
            intent.putExtra("username", usernameS);
            intent.putExtra("email", emailS);
            intent.putExtra("password", passwordS);

            //Add Transitions
            Pair[] pairs = new Pair[3];

            pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
            pairs[1] = new Pair<View, String>(nextBtn, "transition_next_btn");
            pairs[2] = new Pair<View, String>(titleTxt, "transition_title_text");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);

            startActivity(intent, options.toBundle());
        });
    }

    void goBackBtn() {
        backBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class)));
    }

    private boolean validateFullName() {
        String val = fullname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullname.setError("Field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty!");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username too big!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No white spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-0._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty!");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        Pattern PASSWORD_PATTERN = Pattern.compile(
                "^(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                //"(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])" +
                ".{6,20}" +
                "$");

        if (val.isEmpty()) {
            password.setError("Field cannot be empty!");
            password.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(val).matches()) {
            password.setError("Password should contain a minimum of 8 letters, 1 uppercase, 1 lowercase and a special character!");
            password.requestFocus();
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }
}



























