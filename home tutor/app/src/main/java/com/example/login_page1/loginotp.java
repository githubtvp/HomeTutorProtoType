package com.example.login_page1;

import static com.example.login_page1.ExtensionsKt.nextPg;

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

public class loginotp extends AppCompatActivity {

    String phoneNumber;
    Long timeoutseconnds=60L;
    String verficationCode;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    EditText otpinput;
    Button Nextbtn;
    ProgressBar progressBar;
    TextView resendOtptextview;

    FirebaseAuth mauth=FirebaseAuth.getInstance();
    private Class<?> nextPageCreateProfile = CreateProfile.class;
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
            //  gets the email, userid and pwd, phoneno

        });
        otpinput=findViewById(R.id.login_otp);
        Nextbtn=findViewById(R.id.verfiy);
        progressBar=findViewById(R.id.login_pcbar);
        resendOtptextview=findViewById(R.id.resend_otp_tv);



        phoneNumber =getIntent().getExtras().getString("phone");
        //Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();
        sendotp(phoneNumber,false);

        Nextbtn.setOnClickListener(v ->
        {
            String enterOtp = otpinput.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationCode,enterOtp);
            Signin(credential);
            //setInprogress(true);
        });
        resendOtptextview.setOnClickListener((v -> {
            sendotp(phoneNumber,true);
        }));

    }

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
            Nextbtn.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            Nextbtn.setVisibility(View.VISIBLE);
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
//                    Intent intent =new Intent(loginotp.this,Home_page.class);
//                    intent.putExtra("phone",phoneNumber);
//                    startActivity(intent);
                    //otp is verified
                    //create user

                    nextPage = nextPageCreateProfile;
                    startNextPage();
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
    private void startNextPage() {
        Intent nextPg = new Intent(this, nextPage);
        startActivity(nextPg);
    }



//    private fun createNewUser() {
//        val username = binding.username.text.toString()
//        val email = binding.email.text.toString()
//        val pwd = binding.password.text.toString()
//        Page1.auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
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


}