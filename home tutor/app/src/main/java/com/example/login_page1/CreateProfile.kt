package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityCreateProfileBinding

class CreateProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProfileBinding
    private var nextPage: Class<*> = CommonProfile::class.java
    private var userType: String = "0" // Initialize to some default value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        init()
    }

    private fun  init()
    {
       // var nextPg : Intent  //(this, nextPage)
        binding.imageView1.setOnClickListener {
            userType = "1"
            navigateToNextPage()
        }
        binding.imageView2.setOnClickListener {
            // Handle click event
            userType = "2"
            navigateToNextPage()
        }
    }

    private fun navigateToNextPage() {
        try {
            val nextPg = Intent(this, nextPage)
            nextPg.putExtra("userTypeVal", userType)
            startActivity(nextPg)
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception appropriately, such as logging or displaying an error message
        }
    }

    fun pr(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
