package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.TextWatcher
import android.text.Editable
import com.example.login.databinding.ActivityHomePageBinding
import com.example.login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setContentView(R.layout.activity_main)
//        val loginBtn = findViewById<Button>(R.id.btnLogin)
        //val loginBtn = findViewById<Button>(R.id.btnLogin)

    //    val edtTxtUserName = findViewById<EditText>(R.id.editTextUserName)
     //   val edtTxtPasswd = findViewById<EditText>(R.id.editTextPasswd)
    //    val userName = edtTxtUserName.text.toString()
    //    val passwd = edtTxtPasswd.text.toString()
        binding.editTextPasswd.isEnabled = false
        binding.btnLogin  .isEnabled = false
        binding.editTextUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
                val usernameInput = s.toString()
                if (!usernameInput.isEmpty()) {
                   binding.editTextPasswd.isEnabled = true
                } else {
                   binding.editTextPasswd.isEnabled = false
                   binding.btnLogin.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
            }
        })

       binding.editTextPasswd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
                val passwordInput = s.toString()
                if (!passwordInput.isEmpty() && !binding.editTextUserName.text.toString().isEmpty()) {
                   binding.btnLogin.isEnabled = true
                   binding.btnLogin.setOnClickListener{onClickBtnLogIn() }
                } else {
                   binding.btnLogin.isEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
            }
        })

    }//End : override fun onCreate(savedInstanceState: Bundle?)

    fun verified(uName: String, pwd: String): Boolean {
        //  pr("here 1")
        val userName = "tvp"
        val passwd = "tvp"
        return (userName == uName && passwd == pwd)
    }

    fun onClickBtnLogIn()//btn: Button, edtTxtUserName: TextView, edtTxtPasswd: TextView)
     {
       // pr("onClickBtnLogIn : here 1")
        val userName = binding.editTextUserName.text.toString()
        val passwd = binding.editTextPasswd.text.toString()
        //  val btn1 = findViewById<Button>(R.id.btnLogin)
        if (binding.editTextUserName.text.toString().isNotBlank() && binding.editTextPasswd.text.toString().isNotBlank()) {
            if (verified(userName, passwd)) {
             //   pr("Log in Success!")
              //  val logInStatus = "Log in Success!"
                val nextPg = Intent(this, HomePage::class.java)
               // nextPg.putExtra("LogInStatusMsg", logInStatus)
                startActivity(nextPg)
                binding.btnLogin.isEnabled = false
            }
        }
    }

    fun pr(msg: String) {
        Toast.makeText(this, "MainActivituPage : " + msg, Toast.LENGTH_LONG).show()
    }
}