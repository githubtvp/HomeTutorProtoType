package com.example.login_page1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.Page1Binding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Page1 : AppCompatActivity() {

    private lateinit var binding:Page1Binding
    private var nextPageLogin: Class<*> = Login_page::class.java
    private var nextPageSignup: Class<*> = Signup::class.java

    companion object{
        lateinit var auth: FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Page1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this);
        auth = Firebase.auth
        binding.signin.onClick(this@Page1, nextPageLogin)
        binding.signup.onClick(this@Page1, nextPageSignup)
    }

    fun View.onClick2(activity: Activity, destination: Class<*>) {
        setOnClickListener {
            val intent = Intent(activity, destination)
            activity.startActivity(intent)
        }
    }
}
