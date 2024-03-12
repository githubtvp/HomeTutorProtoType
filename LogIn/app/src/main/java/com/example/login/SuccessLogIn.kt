package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SuccessLogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_log_in)
        val txtV1 = findViewById<TextView>(R.id.logInStatusMsg)
        val receivedText = intent.getStringExtra("LogInStatusMsg")
        txtV1.text = receivedText
    }
}