package com.example.login_page1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.login_page1.databinding.ActivityLoginPageBinding
import com.example.login_page1.databinding.ActivitySignBinding

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var nextPage: Class<*>
    private var nextPageCreateProfile: Class<*> = CreateProfile::class.java
    private var nextPageLoginpage: Class<*> = Login_page::class.java

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
            nextPage = nextPageCreateProfile
            var nextPg = Intent(this, nextPage)
            startActivity(nextPg)
        }

        binding.txtsignin.setOnClickListener{
            nextPage = nextPageLoginpage
            var nextPg = Intent(this, nextPage)
            startActivity(nextPg)
        }
    }

    //create a TextWatcher object to attach to each EditText object
    private val textWatcher = object : TextWatcher {
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


    fun isValidUsername(username: String): Boolean {
        // Define your criteria for a valid username
        val minLength = 3
        val maxLength = 20
        val allowedCharacters = "[a-zA-Z0-9_]+"

        // Check if the username meets the criteria
        return username.length in minLength..maxLength && username.matches(allowedCharacters.toRegex())
    }

}