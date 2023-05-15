package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguidetutorial.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Signup3rdClass extends AppCompatActivity {

    ImageView backBtn;
    TextView titleTxt;
    Button nextBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_signup3rd_class);

        //Hooks
        nextBtn = findViewById(R.id.signup_next_btn);
        backBtn = findViewById(R.id.signup_back_btn);
        titleTxt = findViewById(R.id.signup_title_text);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.phone_number);

        goBackBtn();
        callNextSignupScreen();

    }

    public void callNextSignupScreen(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _fullname = getIntent().getStringExtra("fullname");
                String _username = getIntent().getStringExtra("username");
                String _email = getIntent().getStringExtra("email");
                String _password = getIntent().getStringExtra("password");
                String _date = getIntent().getStringExtra("date");
                String _gender = getIntent().getStringExtra("gender");

                String getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString();
                String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + getUserEnteredPhoneNumber;

                Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

                intent.putExtra("fullname", _fullname);
                intent.putExtra("username", _username);
                intent.putExtra("email", _email);
                intent.putExtra("password", _password);
                intent.putExtra("date", _date);
                intent.putExtra("gender", _gender);
                intent.putExtra("phoneNo", _phoneNo);

                //Add Transitions
                Pair[] pairs = new Pair[3];

                pairs[0] = new Pair<View,String>(backBtn, "transition_back_arrow_btn");
                pairs[1] = new Pair<View,String>(nextBtn, "transition_next_btn");
                pairs[2] = new Pair<View,String>(titleTxt, "transition_title_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup3rdClass.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }

    void goBackBtn(){
        backBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Signup2ndClass.class)));
    }
}