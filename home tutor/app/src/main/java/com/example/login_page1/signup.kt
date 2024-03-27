package com.example.login_page1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivitySignBinding

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private var nextPageCreateProfile: Class<*> = CreateProfile::class.java
    private var nextPageLoginpage: Class<*> = Login_page::class.java
    private var prevPagePage1: Class<*> = Page1::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.prevPage.setOnClickListener {
            nextPg(prevPagePage1)
        }

        binding.txtSignin.setOnClickListener {
              nextPg(nextPageLoginpage)
        }
        //  setUpListenerWatchers()
        setFocusLost()
    }

    private fun setFocusLost() {
        binding.btnSignup.isEnabled = false
        // Assuming editTexts is a list of EditText instances
        val messages = listOf(
            "Invalid UserName",
            "Invalid Email",
            "Invalid Password",
            "Reentry Password mismatch"
            // Add more messages for each EditText as needed
        )
        // Initialize the list of EditTexts
        val editTexts =
            listOf(binding.username, binding.email, binding.password, binding.reenterPwd)
        for ((index, editText) in editTexts.withIndex()) {
            val message = messages.getOrNull(index) ?: "Default message"
            editText.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    // This block will be executed when the EditText loses focus
                    val text = editText.text.toString()
                    // Perform validation based on the type of EditText
                    val isValid = when (editText) {
                        // Add cases for each EditText requiring different validation
                        // Example: Email validation
                        binding.email -> isValidEmail(text)
                        // Example: Username validation
                        binding.username -> isValidUsername(text)
                        binding.password -> isValidPassword(text)
                        binding.reenterPwd -> isValidReenterPassword(text)

                        // Add more cases as needed
                        else -> true // Default to true if no specific validation is needed
                    }
                    // Perform action based on validation result
                    if (!isValid) {
                        // Show a toast message or perform any other action to notify the user
                        pr(message)
                        editText.requestFocus()
                        // Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                    }
                    binding.btnSignup.isEnabled = isValid
                    if (isValid) {
                       // binding.btnSignup.setOnClickListener { onBtnSignUpClick() }
                        binding.btnSignup.onClick(this@Signup, nextPageCreateProfile)
                    }
                }
            }
        }

    }

    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields
        binding.username.addTextChangedListener(getTextWatcher)
        binding.email.addTextChangedListener(getTextWatcher)
        binding.password.addTextChangedListener(getTextWatcher)
        binding.reenterPwd.addTextChangedListener(getTextWatcher)
        binding.btnSignup.isEnabled = false
    }

    //create a TextWatcher object to attach to each EditText object
    private val getTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No implementation needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No implementation needed
        }

        override fun afterTextChanged(s: Editable) {
            // Validate all EditText fields
            val isValid = validateEditTexts()
            // Enable or disable the forward arrow button based on validation result
            binding.btnSignup.isEnabled = isValid
            if (isValid) {
              //  binding.btnSignup.setOnClickListener { onBtnSignUpClick() }
               binding.btnSignup.onClick(this@Signup, nextPageCreateProfile)
            }
        }
    }

    private fun validateEditTexts(): Boolean {
        val text1 = binding.username.text.toString().trim()
        val text2 = binding.email.text.toString().trim()
        val text3 = binding.password.text.toString().trim()
        val text4 = binding.reenterPwd.text.toString().trim()

        // Perform validation for each EditText field
        val isValidText1 = text1.isNotEmpty() && isValidUsername(text1)// Example validation logic
        val isValidText2 = text2.isNotEmpty() && isValidEmail(text2)// Example validation logic
        val isValidText3 = text3.isNotEmpty() && isValidPassword(text3)// Example validation logic
        val isValidText4 =
            text4.isNotEmpty() && isValidReenterPassword(text4)// Example validation logic
        // Return true if all EditText fields are valid, otherwise false
        return isValidText1 && isValidText2 && isValidText3 && isValidText4 //&& isValidText5
    }

    private fun isValidUsername(username: String): Boolean {
        val userNameChk: Boolean = chkUserName(username)
        if (!userNameChk) {
            pr("Invalid Username entry!")
            binding.username.requestFocus() // Keep focus on this EditText
        }
        return userNameChk
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        val emailChk = pattern.matcher(email).matches()
        if (!emailChk) {
            pr("Invalid Email!")
            binding.email.requestFocus() // Keep focus on this EditText
        }
        return emailChk
    }

    private fun isValidPassword(pwd: String): Boolean {
        val pwdChk = chkPassword(pwd)
        if (!pwdChk) {
            pr("Invalid Password entry!")
            binding.password.requestFocus() // Keep focus on this EditText
        }
        return pwdChk
    }

    private fun isValidReenterPassword(pwd: String): Boolean {
        val reentPwd: Boolean = (binding.password.text.toString() == pwd)
        if (reentPwd) {
            pr("Password reentry mismatch!")
            binding.reenterPwd.requestFocus() // Keep focus on this EditText
        }
        return reentPwd
    }

    private fun onBtnSignUpClick() {
        nextPg(nextPageCreateProfile)
    }

}
