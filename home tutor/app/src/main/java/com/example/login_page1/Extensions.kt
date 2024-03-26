package com.example.login_page1

// Extensions.kt
import android.content.Context
import android.widget.Toast

fun Context.pr(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
