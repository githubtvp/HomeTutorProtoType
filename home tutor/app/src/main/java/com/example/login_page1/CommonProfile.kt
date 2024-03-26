package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityCommonProfileBinding

class CommonProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCommonProfileBinding
    private lateinit var nextPage: Class<*>
    private var nextPage1: Class<*> = StudProfile::class.java
    private var nextPage2: Class<*> = TutorProfile::class.java
    private var userTypeVal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        val uType = intent.getStringExtra("userTypeVal").toString()
        if (uType != null) {
            userTypeVal = uType.toInt()
        } else {
            // Handle the case where "userTypeVal" is not set in the intent extras
        }
       // val userTypeVal : Int = uType.toInt()
        setUpListenerWatchers()
    }

    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields
        binding.editTxtName.addTextChangedListener(getTextWatcher)
        binding.editTxtAge.addTextChangedListener(getTextWatcher)
        binding.editTxtAdd.addTextChangedListener(getTextWatcher)
        binding.editTxtCity.addTextChangedListener(getTextWatcher)
        binding.editTxtEmail.addTextChangedListener(getTextWatcher)
        // Disable forward arrow button initially
        binding.fwdArr.isEnabled = false
    }


    //create a TextWatcher object to attach to each EditText object
    private val getTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No implementation needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No implementation needed
        }

        override fun afterTextChanged(s: Editable?) {
            // Validate all EditText fields
            val isValid = validateEditTexts()
            // Enable or disable the forward arrow button based on validation result
            binding.fwdArr.isEnabled = isValid
            if (isValid) {
                binding.fwdArr.setOnClickListener { onFwdArrClickNextPage() }
            }
        }
    }

    private fun validateEditTexts(): Boolean {
        val text1 = binding.editTxtName.text.toString().trim()
        val text2 = binding.editTxtAge.text.toString().trim()
        val text3 = binding.editTxtAdd.text.toString().trim()
        val text4 = binding.editTxtCity.text.toString().trim()
        val text5 = binding.editTxtEmail.text.toString().trim()

        // Perform validation for each EditText field
        val isValidText1 = text1.isNotEmpty() // Example validation logic
        val isValidText2 = text2.isNotEmpty() // Example validation logic
        val isValidText3 = text3.isNotEmpty() // Example validation logic
        val isValidText4 = text4.isNotEmpty() // Example validation logic
        val isValidText5 = text5.isNotEmpty() // Example validation logic

        // Return true if all EditText fields are valid, otherwise false
        return isValidText1 && isValidText2 && isValidText3 && isValidText4 && isValidText5
    }

    private fun onFwdArrClickNextPage() {

        if (1 == userTypeVal) {
            pr("Student user!")
            nextPage = nextPage1
        } else if (2 == userTypeVal) {
            pr("Tutor user!")
            nextPage = nextPage2
        }
        pr("All text boxes validated!")
        val nextPg = Intent(this, nextPage)
        startActivity(nextPg)
    }

    fun pr(msg: String) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show()
    }

}//End- class CommonProfile : AppCompatActivity()
