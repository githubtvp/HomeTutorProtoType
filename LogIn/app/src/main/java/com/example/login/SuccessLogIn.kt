package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class SuccessLogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_log_in)
        val txtV1 = findViewById<TextView>(R.id.logInStatusMsg)
        val receivedText = intent.getStringExtra("LogInStatusMsg")
        txtV1.text = "Success Page : " + receivedText
    }
    fun pr(msg: String) {
        Toast.makeText(this, "SuccessLogInPage : " + msg, Toast.LENGTH_LONG).show()
    }
}