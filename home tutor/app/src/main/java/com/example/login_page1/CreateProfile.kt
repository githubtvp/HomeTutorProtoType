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
   // private var nextPage: Class<*> = CommonProfile::class.java

    private var nextPage: Class<*>? = try {
        CommonProfile::class.java
    } catch (e: ClassNotFoundException) {
        pr(e.toString())
        // Handle the exception appropriately
        null
    }

    private var userType: Int = 0 // Initialize to some default value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.imageView1.setOnClickListener {
            // Handle click event
            userType = 1
          //  pr("before next IMV1")
            navigateToNextPage()
        }
        binding.textView1.setOnClickListener {
            // Handle click event
            userType = 1
            pr("before next Text 1")
            navigateToNextPage()
        }
        binding.imageView2.setOnClickListener {
            // Handle click event
            userType = 2
            navigateToNextPage()
        }
      binding.textView2.setOnClickListener {
            // Handle click event
          userType = 2
          navigateToNextPage()
        }

    }

    private fun navigateToNextPage() {
        // You can start the next activity here
      //  pr("H" + nextPage.toString())
      //  val nextPg = Intent(this, nextPage)
      //  nextPg.putExtra("userTypeVal", userType)
        val nextPg = Intent(this,CommonProfile::class.java )
        startActivity(nextPg)
        // Optionally, you can finish the current activity if you don't want to keep it in the back stack
        // finish()
    }
    fun pr(msg: String) {
      //  Toast.makeText(this, "Create Page : " + msg, Toast.LENGTH_LONG).show()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
