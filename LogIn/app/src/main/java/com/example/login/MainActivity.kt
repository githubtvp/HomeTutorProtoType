package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btnLogIn)
        var loginStatus = "Succesful LogIn"
        btn1.setOnClickListener { onClickBtnLogIn(loginStatus) }
    }

    fun onClickBtnLogIn(logInStatus: String)
    {
        Toast.makeText(this, "Here 1", Toast.LENGTH_LONG).show()
        val nextPg = Intent(this, SuccessLogIn::class.java)
        nextPg.putExtra("LogInStatusMsg", logInStatus)
        startActivity(nextPg)

    }
}