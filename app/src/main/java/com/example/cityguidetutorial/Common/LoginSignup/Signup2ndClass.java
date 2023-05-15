package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguidetutorial.R;

import java.util.Calendar;

public class Signup2ndClass extends AppCompatActivity {

    ImageView backBtn;
    TextView titleTxt;
    Button nextBtn, loginBtn;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_signup2nd_class);

        //Hooks
        nextBtn = findViewById(R.id.signup_next_btn);
        backBtn = findViewById(R.id.signup_back_btn);
        titleTxt = findViewById(R.id.signup_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

        callNextSignupScreen();
        goBackBtn();
    }

    public void callNextSignupScreen() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateGender() | !validateAge()) {
                    return;
                }

                selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
                String gender = selectedGender.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String date = day + "/" + month + "/" + year;

                String fullname = getIntent().getStringExtra("fullname");
                String username = getIntent().getStringExtra("username");
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");

                Intent intent = new Intent(getApplicationContext(), Signup3rdClass.class);
                intent.putExtra("fullname", fullname);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("date", date);
                intent.putExtra("gender", gender);

                //Add Transitions
                Pair[] pairs = new Pair[3];

                pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
                pairs[1] = new Pair<View, String>(nextBtn, "transition_next_btn");
                pairs[2] = new Pair<View, String>(titleTxt, "transition_title_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup2ndClass.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }

    void goBackBtn() {
        backBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Signup.class)));
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }
}