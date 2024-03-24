package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityLoginPageBinding

class Login_page : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var nextPage: Class<*>
    private var nextPageSuc: Class<*> = Home_page::class.java
    private var nextPageFail: Class<*> = Login_page::class.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignin.isEnabled = false
        initUI()
    }

    private fun initUI() {

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
                val usernameInput = s.toString()
                if (!usernameInput.isEmpty()) {
                    binding.Password.isEnabled = true
                } else {
                    binding.Password.isEnabled = false
                    binding.btnSignin.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
            }
        })

        binding.Password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
                val passwordInput = s.toString()
                if (!passwordInput.isEmpty() && !binding.email.text.toString().isEmpty()) {
                    binding.btnSignin.isEnabled = true
                    binding.btnSignin.setOnClickListener { onClickBtnLogIn() }
                } else {
                    binding.btnSignin.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
            }
        })

        binding.FabBack.setOnClickListener {
            val intent = Intent(this, page1::class.java)
            startActivity(intent)
        }

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener {

        }
    }

    fun verified(uName: String, pwd: String): Boolean {
        val userName = "t"
        val passwd = "t"
        return (userName == uName && passwd == pwd)
    }

    //fun onClickBtnLogIn(btn: Button, edtTxtUserName: TextView, edtTxtPasswd: TextView) {
    fun onClickBtnLogIn() //btn: Button, edtTxtUserName: TextView, edtTxtPasswd: TextView) {
    {   // pr("onClickBtnLogIn : here 1")
        var nextPg = Intent(this, Home_page::class.java)
        if (binding.email.text.toString().isNotBlank() && binding.Password.text.toString()
                .isNotBlank()
        ) {
            if (verified(binding.email.text.toString(), binding.Password.text.toString())) {
              //  pr("Log in Success!")
                nextPage = nextPageSuc //(this, Home_page::class.java)
               // binding.btnSignin.isEnabled = false
            }
            else
            {//  pr("Log in Failure!")
                nextPage = nextPageFail
            }
            nextPg = Intent(this, nextPage)
            binding.btnSignin.isEnabled = false
        }
        startActivity(nextPg)
    }
    fun pr(msg: String) {
        Toast.makeText(this, "Login Page : " + msg, Toast.LENGTH_LONG).show()
    }
}