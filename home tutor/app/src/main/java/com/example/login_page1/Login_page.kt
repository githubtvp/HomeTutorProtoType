package com.example.login_page1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login_page1.databinding.ActivityLoginPageBinding

class Login_page : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityLoginPageBinding.inflate(layoutInflater)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initalize UI
        initUI()
    }
    private fun initUI(){
        binding.FabBack.setOnClickListener{
            val intent = Intent(this, page1::class.java)
            startActivity(intent)
        }

        binding.btnSignin.setOnClickListener{

        }

        binding.tvSignup.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener{

        }
    }
}