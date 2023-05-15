package com.example.cityguidetutorial.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cityguidetutorial.Databases.SessionManager;
import com.example.cityguidetutorial.LocationOwner.RetailerDashboard;
import com.example.cityguidetutorial.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    ImageView goBackBtn;
    Button loginBtn, createAccountBtn, forgotPasswordBtn;
    TextInputLayout phoneNumber, password;
    TextInputEditText phoneEditTxt, passwordEditTxt;
    RelativeLayout progressBar;
    CountryCodePicker countryCodePicker;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        //Hooks
        goBackBtn = findViewById(R.id.login_back_btn);
        loginBtn = findViewById(R.id.login_login_btn);
        createAccountBtn = findViewById(R.id.login_create_account_btn);
        forgotPasswordBtn = findViewById(R.id.login_forget_password_btn);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        progressBar = findViewById(R.id.login_progress_bar);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        rememberMe = findViewById(R.id.remember_me);
        phoneEditTxt = findViewById(R.id.login_phone_editTxt);
        passwordEditTxt = findViewById(R.id.login_password_editTxt);


        //Methods
        goBackBtn();
        goToSignUpScreen();
        login();
        goToForgetPasswordScreen();

        //Check if phoneNo and password is saved in Shared Preferences
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            phoneEditTxt.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditTxt.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }

    void login() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(Login.this)) {
                    showCustomDialog();
                }

                //Validate fields
                if (!validateFields()) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Get values
                String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
                String _password = password.getEditText().getText().toString().trim();

                //Remove zeros at the start
                if (_phoneNumber.charAt(0) == '0') {
                    _phoneNumber = _phoneNumber.substring(1);
                }
                //Join selected countrycodepicker with phone number
                String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

                //Check if user exists in database
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

                if (rememberMe.isChecked()){
                    SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
                    sessionManager.createRememberMeSession(_phoneNumber, _password);
                }

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phoneNumber.setError(null);
                            phoneNumber.setErrorEnabled(false);

                            String sysPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                            //if password exists and match with users password then get other fields from database
                            if (sysPassword.equals(_password)) {
                                password.setError(null);
                                password.setErrorEnabled(false);

                                //Get users data from firebase database
                                String _fullname = snapshot.child(_completePhoneNumber).child("fullname").getValue(String.class);
                                String _username = snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                                String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                                String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                                String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                                String _dateOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                                String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);

                                //Create Session
                                SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                                sessionManager.createLoginSession(_fullname, _username, _email, _phoneNo, _password, _dateOfBirth, _gender);

                                startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));

                                progressBar.setVisibility(View.GONE);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) return false;
        return networkInfo.isConnected();
    }

    void goToSignUpScreen() {
        createAccountBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Signup.class)));
    }

    void goToForgetPasswordScreen() {
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
            }
        });
    }

    void goBackBtn() {
        goBackBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class)));
    }

    private boolean validateFields() {

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

    }
}