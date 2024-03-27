package com.example.login_page1

// Extensions.kt
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val MIN_USERNAME_LEN = 3
const val MAX_USERNAME_LEN = 20
const val USERNAME_ALLOWED_CHAR = "[a-zA-Z0-9_]+"
const val PWD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"


fun Context.pr(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.nextPg(nextPage: Class<*>) {
    val intent = Intent(this, nextPage)
    startActivity(intent)
}

fun View.onClick(context: Context, nextPage: Class<*>) {
    this.setOnClickListener {
        if (this is Button || this is FloatingActionButton || this is TextView) {
            context.nextPg(nextPage)
        }
    }
}

fun chkUserName(username: String): Boolean {
    // Define your criteria for a valid username
    val minLength = MIN_USERNAME_LEN
    val maxLength = MAX_USERNAME_LEN
    val allowedCharacters = USERNAME_ALLOWED_CHAR   //"[a-zA-Z0-9_]+"
   return (username.length in minLength..maxLength && username.matches(allowedCharacters.toRegex()))
}

fun chkPassword(pwd: String): Boolean {
    val passwordRegex =
        PWD_REGEX //  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    return (pwd.matches(Regex(passwordRegex)))
}