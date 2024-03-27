package com.example.login_page1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.Page1Binding

class Page1 : AppCompatActivity() {

    private lateinit var binding:Page1Binding
    private var nextPageLogin: Class<*> = Login_page::class.java
    private var nextPageSignup: Class<*> = Signup::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Page1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signin.onClick(this@Page1, nextPageLogin)
        binding.signup.onClick(this@Page1, nextPageSignup)
    }
}
