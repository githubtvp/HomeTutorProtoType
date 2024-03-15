package com.example.login_page1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login_page1.databinding.ActivityLoginPageBinding
import com.example.login_page1.databinding.ActivitySignBinding

class signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initalize UI
        initUI()
    }
    private fun initUI()
    {
        binding.FabBack.setOnClickListener{
            val intent = Intent(this, page1::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener{

        }

        binding.txtsignin.setOnClickListener{
            val intent = Intent(this, Login_page::class.java)
            startActivity(intent)
        }


    }
}