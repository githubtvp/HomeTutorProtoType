package com.example.login_page1

// Extensions.kt
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.view.View
import android.widget.Button

fun Context.pr(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.newPg(nextPage: Class<*>) {
    val intent = Intent(this, nextPage)
    startActivity(intent)
}

fun View.onClick(context: Context, nextPage: Class<*>) {
    this.setOnClickListener {
        if (this is Button) {
            context.newPg(nextPage)
        }
    }
}