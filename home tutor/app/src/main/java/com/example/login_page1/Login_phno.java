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

public class login_phno extends AppCompatActivity {

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

//        //get the userid, passwd and email from the previous extras sent
       // thePage = prevPage;
        String username; // = binding.username.text.toString()
        String email; // = binding.email.text.toString()
        String password; // = binding.password.text.toString()
      //  Intent prevPg = new Intent(this, Signup.class);
       // String uname = prevPg.getStringExtra("username").toString() ;
     //   ExtensionsKt.pr(this, "User name :  " + uname);
     //   val value1 = intent.getStringExtra("VALUE1")
      //  username = ExtensionsKt.getStrFmPrevPg(login_phno.this, "username", thePage);
       // ExtensionsKt.pr(this, prevPg.getComponent().getClassName());

        Intent prevIntent = getIntent();
        username = prevIntent.getStringExtra("username");
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
      //  ExtensionsKt.pr(login_phno.this,"Login : User name : " + username);
      //  ExtensionsKt.pr(login_phno.this,"Login : email : " + email);
        ExtensionsKt.pr(this,"Login : password : " + password);

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
           // goToSendOTPPage();
            //send to next page userid, pwd, email, phoneno
//            Intent intent=new Intent(Login_phno.this, Loginotp.class);
//            intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
//            ExtensionsKt.pr(this,"Here 11");
//            startActivity(intent);
//            ExtensionsKt.pr(this,"Here 2222");
            nextPage = nextPageLoginOTP;
          //  ExtensionsKt.passStrtoNextPg(this,"username", username, nextPage);
//            ExtensionsKt.passStrtoNextPg(this, "email", email, nextPage);
//            ExtensionsKt.passStrtoNextPg(this, "password", password, nextPage);
//            ExtensionsKt.passStrtoNextPg(this, "phone", countryCodePicker.getFullNumberWithPlus(), nextPage);
//            ExtensionsKt.nextPg(this, nextPage);

            Intent nextIntent = new Intent(this, nextPage);
            nextIntent.putExtra("username", username);
            nextIntent.putExtra("password", password);
            nextIntent.putExtra("email", email);
            nextIntent.putExtra("phoneno", countryCodePicker.getFullNumberWithPlus());
            startActivity(nextIntent);

        } );
    }

}
