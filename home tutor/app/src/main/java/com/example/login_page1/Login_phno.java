package com.example.login_page1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class Login_phno extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phoneInput;
    Button sendOtpbtn;
    ProgressBar progressBar;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})

    private Class<?> nextPageLoginOTP = Loginotp.class;
    private Class<?> nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_phno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String username;
        String email;
        String password;
        Intent prevIntent = getIntent();
        username = prevIntent.getStringExtra("username");
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
      //  ExtensionsKt.pr(this,"Login : password : " + password);

        countryCodePicker = findViewById(R.id.login_countrycode);
        phoneInput=findViewById(R.id.login_mobilenumber);
        sendOtpbtn=findViewById(R.id.login_sendotp);
        progressBar=findViewById(R.id.login_pcbar);

        progressBar.setVisibility(View.GONE);

        countryCodePicker.registerCarrierNumberEditText(phoneInput);
        sendOtpbtn.setOnClickListener((v) ->{
            if(!countryCodePicker.isValidFullNumber()){
                phoneInput.setError("phone is invalid");
                return;
            }
            nextPage = nextPageLoginOTP;
            Intent nextIntent = new Intent(this, nextPage);
            nextIntent.putExtra("username", username);
            nextIntent.putExtra("password", password);
            nextIntent.putExtra("email", email);
            nextIntent.putExtra("phoneno", countryCodePicker.getFullNumberWithPlus());
            startActivity(nextIntent);
        } );
    }

}
