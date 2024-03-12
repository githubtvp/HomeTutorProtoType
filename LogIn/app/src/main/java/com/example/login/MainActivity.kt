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
        var loginStatus = "Log in succesfull"
        btn1.setOnClickListener { onClickBtnLogIn(loginStatus) }
    }

    fun onClickBtnLogIn(logInStatus: String)
    {
       // pr("here 1")
        val nextPg = Intent(this, SuccessLogIn::class.java)
        nextPg.putExtra("LogInStatusMsg", logInStatus)
        startActivity(nextPg)
    }
    fun pr(msg : String)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}