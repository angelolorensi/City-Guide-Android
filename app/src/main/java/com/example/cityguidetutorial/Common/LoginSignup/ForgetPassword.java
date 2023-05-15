package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cityguidetutorial.HelperClasses.CheckInternet;
import com.example.cityguidetutorial.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNo;
    Button nextBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_forget_password);

        //Hooks
        countryCodePicker = findViewById(R.id.forget_password_country_code_picker);
        phoneNo = findViewById(R.id.forget_password_phone_number);
        nextBtn = findViewById(R.id.forget_password_next_button);
        progressBar = findViewById(R.id.forget_password_progress_bar);

        //Methods
        verifyPhoneNumber();
    }

    private void verifyPhoneNumber() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check connection
                CheckInternet checkInternet = new CheckInternet();
                if (!checkInternet.isConnected(ForgetPassword.this)) {
                    checkInternet.showCustomDialog(ForgetPassword.this);
                }

                //Validate Phone Number
                if (!validateFields()) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Get data from fields
                String _phoneNumber = phoneNo.getEditText().getText().toString().trim();
                if (_phoneNumber.charAt(0) == '0') {
                    _phoneNumber = _phoneNumber.substring(1);
                }
                String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

                //Check if user exists in database
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //If user in database call OTP and verify if its the users phone
                        if (snapshot.exists()) {
                            phoneNo.setError(null);
                            phoneNo.setErrorEnabled(false);

                            Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                            intent.putExtra("phoneNo", _completePhoneNumber);
                            intent.putExtra("whatToDo", "updateData");
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            phoneNo.setError("No such user phone number is registered");
                            phoneNo.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validateFields() {
        String _phoneNumber = phoneNo.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNo.setError("Phone number cannot be empty");
            phoneNo.requestFocus();
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }
    }


}