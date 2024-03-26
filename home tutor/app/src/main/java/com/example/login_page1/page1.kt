package com.example.login_page1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Page1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1)

        val sign = findViewById<Button>(R.id.sign)
        val log_in = findViewById<TextView>(R.id.page2)
       // var log="hello"
        sign.setOnClickListener{OnClickBtnLogin()}
        log_in.setOnClickListener { OnClicktxtLogin() }
    }
    fun OnClickBtnLogin() {
          // pr("succes")
            val intent = Intent(this,Login_page::class.java)
           startActivity(intent)
    }
    fun OnClicktxtLogin() {
        // pr("succes")
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
    fun pr(msg : String)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }
}
