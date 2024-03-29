package com.example.login_page1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityLoginPageBinding

class Login_page : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var nextPage: Class<*>
    private var nextPageSuc: Class<*> = Home_page::class.java
    private var nextPageFail: Class<*> = Login_page::class.java
    private var nextPageSignup: Class<*> = Signup::class.java
    private var prevPagePage1: Class<*> = Page1::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignin.isEnabled = false
        initUI()
    }

    private fun initUI() {

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
                val usernameInput = s.toString()
                if (!usernameInput.isEmpty()) {
                    binding.password.isEnabled = true
                } else {
                    binding.password.isEnabled = false
                    binding.btnSignin.isEnabled = false
                }
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do something before text changed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do something when text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Do something after text changed
                val passwordInput = s.toString()
                if (!passwordInput.isEmpty() && !binding.username.text.toString().isEmpty()) {
                    binding.btnSignin.isEnabled = true
                    binding.btnSignin.setOnClickListener { onClickBtnLogIn() }
                } else {
                    binding.btnSignin.isEnabled = false
                }

            }
        })

        binding.prevPage.onClick(this@Login_page, prevPagePage1)
        binding.signup.onClick(this@Login_page, nextPageSignup)

        binding.forgotPassword.setOnClickListener {

        }
        setFocusLost()
    }

    private fun setFocusLost() {
        binding.btnSignin.isEnabled = false
        // Assuming editTexts is a list of EditText instances
        val messages = listOf(
            "Invalid UserName",
            "Invalid Password"
            // Add more messages for each EditText as needed
        )
        // Initialize the list of EditTexts
        val editTexts =
            listOf(binding.username, binding.password)
        for ((index, editText) in editTexts.withIndex()) {
            val message = messages.getOrNull(index) ?: "Default message"
            editText.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    // This block will be executed when the EditText loses focus
                    val text = editText.text.toString()
                    // Perform validation based on the type of EditText
                    val isValid = when (editText) {
                        // Add cases for each EditText requiring different validation
                        // Example: Username validation
                        binding.username -> isValidUsername(text)
                        binding.password -> isValidPassword(text)
                        // Add more cases as needed
                        else -> true // Default to true if no specific validation is needed
                    }
                    // Perform action based on validation result
                    if (!isValid) {
                        // Show a toast message or perform any other action to notify the user
                        pr(message)
                      //  editText.requestFocus()
                    }
                    binding.btnSignin.isEnabled = isValid
                    if (isValid) {
                        binding.btnSignin.setOnClickListener { onClickBtnLogIn() }
                    }
                }
            }
        }
    }

    private fun isValidUsername(username: String): Boolean {
        val userNameChk = chkUserName(username)
        if (!userNameChk) {
            pr("Invalid Username entry!")
            binding.username.requestFocus() // Keep focus on this EditText
        }
        return userNameChk
    }

    private fun isValidPassword(pwd: String): Boolean {
        val pwdChk = chkPassword(pwd)
        if (!pwdChk) {
            pr("Invalid Password entry!")
            binding.password.requestFocus() // Keep focus on this EditText
        }
        return pwdChk
    }

    private fun verified(uName: String, pwd: String): Boolean {
        val userName = "t"
        val passwd = "t"
        return (userName == uName && passwd == pwd)
    }

    //fun onClickBtnLogIn(btn: Button, edtTxtUserName: TextView, edtTxtPasswd: TextView) {
    fun onClickBtnLogIn() //btn: Button, edtTxtUserName: TextView, edtTxtPasswd: TextView) {
    {
        if (binding.username.text.toString().isNotBlank() && binding.password.text.toString()
                .isNotBlank()
        ) {
            if (verified(binding.username.text.toString(), binding.password.text.toString())) {
                nextPage = nextPageSuc
            }
            else {
                nextPage = nextPageFail
            }
            binding.btnSignin.isEnabled = false
        }
        nextPg(nextPage)
    }
}