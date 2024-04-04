package com.example.login_page1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_page1.utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Loginotp extends AppCompatActivity {

    String username; // = binding.username.text.toString()
    String email; // = binding.email.text.toString()
    String password; // = binding.password.text.toString()
    String phoneNo;
    Long timeoutseconnds=60L;
    String verficationCode;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    EditText otpinput;
    Button verifyBtn;
    ProgressBar progressBar;
    TextView resendOtptextview;

    FirebaseAuth mauth = FirebaseAuth.getInstance();
    private Class<?> nextPageLogin = Login_page.class;

    private Class<?> prevPage = Login_phno.class;
    private Class<?> nextPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginotp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent prevIntent = getIntent();
        username = prevIntent.getStringExtra("username");
        email = prevIntent.getStringExtra("email");
        password = prevIntent.getStringExtra("password");
        phoneNo =prevIntent.getStringExtra("phoneno");

        otpinput=findViewById(R.id.login_otp);
        verifyBtn=findViewById(R.id.verfiy);
        progressBar=findViewById(R.id.login_pcbar);
        resendOtptextview=findViewById(R.id.resend_otp_tv);
        sendotp(phoneNo,false);

        verifyBtn.setOnClickListener(v ->
        {
            String enterOtp = otpinput.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationCode,enterOtp);
            Signin(credential);
            //setInprogress(true);
        });

        resendOtptextview.setOnClickListener((v -> {
            sendotp(phoneNo,true);
        }));

    }//End - onCreate

   void  sendotp(String phoneNumber,boolean isResend){
        startResendTimer();
        setInprogress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutseconnds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Signin(phoneAuthCredential);
                                setInprogress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                                setInprogress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verficationCode=s;
                                forceResendingToken=ResendingToken;
                                AndroidUtil.showToast(getApplicationContext(),"OTP Sent successfully");
                                setInprogress(false);
                            }
                        });
        if(isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
        }
        else
        {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInprogress(boolean inprogress){
        if(inprogress){
            progressBar.setVisibility(View.VISIBLE);
            verifyBtn.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            verifyBtn.setVisibility(View.VISIBLE);
        }
    }

    void Signin(PhoneAuthCredential PhoneAuthCredential){
        //login and move to next activity
        setInprogress(true);
        mauth.signInWithCredential(PhoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInprogress(false);
                if(task.isSuccessful())
                {
                   createNewUser();
                }else {
                    AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                }
            }
        });
    }
    void  startResendTimer(){
        resendOtptextview.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutseconnds--;
                resendOtptextview.setText("Resend OTP in"+timeoutseconnds+"seconds");
                if(timeoutseconnds<=0)
                {
                    timeoutseconnds=60l;
                    timer.cancel();
                    runOnUiThread(()->
                    {
                        resendOtptextview.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }
//    private void startNextPage() {
//        Intent nextPg = new Intent(this, nextPage);
//        startActivity(nextPg);
//    }

//    private fun createNewUser() {
//      //  val username = binding.username.text.toString()
//     //   val email = binding.email.text.toString()
//      //  val pwd = binding.password.text.toString()
//        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                pr("New User created successfully")
//                //binding.btnSignup.onClick(this@Signup, nextPageCreateProfile)
//
//                nextPg(nextPageCreateProfile)
//                finish()
//            } else {
//                pr("New User creation failed XXX")
//            }
//        }
//    }

    private void createNewUser() {
       mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ExtensionsKt.pr(Loginotp.this, "New User created successfully");
                            goToLoginPage();
                        } else {
                            ExtensionsKt.pr(Loginotp.this,"User cr failed XXX\n" + task.getException().getMessage());
                        }
                    }
                });
    }

    private void goToLoginPage()
    {
        Intent nextIntent = new Intent(this,Login_page.class);
        startActivity(nextIntent);
        finish();
    }
}