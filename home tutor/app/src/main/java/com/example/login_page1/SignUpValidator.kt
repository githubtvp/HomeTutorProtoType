package com.example.login_page1
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


//const val MIN_USERNAME_LEN = 3
//const val MAX_USERNAME_LEN = 20
//const val USERNAME_ALLOWED_CHAR = "[a-zA-Z0-9_]+"
//const val PWD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"

class SignUpValidator(
    private val context: Context,
    private val button: Button,
    private val usernameEditText: EditText,
    private val emailEditText: EditText,
    private val passwordEditText: EditText

) {
    private var isUsernameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false


    // Function to check if all fields are valid
    fun isAllFieldsValid(): Boolean {
        return isUsernameValid && isPasswordValid && isEmailValid
    }

    private fun validate() {
        button.isEnabled = isUsernameValid && isPasswordValid && isEmailValid
    }

    private fun validateUsername(username: String): Boolean {
        // Implement your username validation logic here
      //  context.pr("here 1")
        return username.isNotEmpty() && isValidUsername(username)// Example validation: not empty
    }

    private fun isValidUsername(username: String): Boolean {
        // Define your criteria for a valid username
        val minLength = MIN_USERNAME_LEN
        val maxLength = MAX_USERNAME_LEN
        val allowedCharacters = USERNAME_ALLOWED_CHAR   //"[a-zA-Z0-9_]+"
        context.pr("here 1")
        // if(username.length <3) pr("User name length less than 3")
        // Check if the username meets the criteria
        return username.length in minLength..maxLength && username.matches(allowedCharacters.toRegex())
    }

    private fun validateEmail(email: String): Boolean {
        // Implement your email validation logic here
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        // Implement your password validation logic here
        return password.isNotEmpty() && isValidPassword(password) // password.length >= 8 // Example validation: at least 8 characters
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = PWD_REGEX //  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        return password.matches(Regex(passwordRegex))
    }

    fun addValidation(editText: EditText, validationMessage: String) {
        val validator: (String) -> Boolean = when (editText) {
            // Add specific validation logic for each EditText
            // For example, for the username EditText
            usernameEditText -> { text -> validateUsername(text) }
            // For the password EditText
            passwordEditText -> { text -> validatePassword(text) }
            // For the email EditText
            emailEditText -> { text -> validateEmail(text) }
            else -> { _ -> false }
        }

        editText.addOnFocusLostListenerWithValidation(context, validationMessage) { text ->
            val isValid = validator(text)
            when (editText) {
                // Update the corresponding validation flag
                usernameEditText -> isUsernameValid = isValid
                passwordEditText -> isPasswordValid = isValid
                emailEditText -> isEmailValid = isValid
            }
            validate()
            isValid
        }
    }
}

fun EditText.addOnFocusLostListenerWithValidation(
    context: Context,
    validationMessage: String,
    validator: (String) -> Boolean
) {
    val editText = this

    // OnFocusChangeListener to detect focus changes
    this.setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            val text = editText.text.toString()
            if (!validator(text)) {
                // Show error message
                Toast.makeText(context, validationMessage, Toast.LENGTH_LONG).show()
                // Optionally, you can set an error indicator in the EditText
                editText.error = validationMessage
            } else {
                // Clear error message if valid
                editText.error = null
            }
        }
    }
}

//fun EditText.addOnFocusLostListenerWithValidation(
//    context: Context,
//    validationMessage: String,
//    validator: (String) -> Boolean
//) {
//    val editText = this
//   // Toast.makeText(context, validationMessage, Toast.LENGTH_LONG).show()
//
//    // TextWatcher to detect text changes
//    val textWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//        override fun afterTextChanged(s: Editable?) {
//            // Do nothing here
//        }
//    }
//
//    // Set TextWatcher to the EditText
//    this.addTextChangedListener(textWatcher)
//
//    // OnFocusChangeListener to detect focus changes
//    this.setOnFocusChangeListener { _, hasFocus ->
//        if (!hasFocus) {
//            val text = editText.text.toString()
//            Toast.makeText(context, validationMessage, Toast.LENGTH_LONG).show()
//            if (text.isNotEmpty() && !validator(text)) {
//                Toast.makeText(context, validationMessage, Toast.LENGTH_LONG).show()
//              //  context.pr(validationMessage)
//                editText.requestFocus() // Keep focus on this EditText
//            }
//        }
//    }
//
//}
