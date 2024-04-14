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
const val MIN_STUD_AGE = 10
const val MAX_STUD_AGE = 30
const val MIN_TUTOR_AGE = 25
const val MAX_TUTOR_AGE = 65
const val USERNAME_ALLOWED_CHAR = "[a-zA-Z0-9_]+"
//const val PWD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
const val PWD_REGEX = "^\\d{6}$"
const val NAME_REGEX = "^[a-zA-Z]+$"
const val PHONE_REGEX = "^\\d{10}$"

fun Context.pr(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.passStrtoNextPg(name: String, str : String, nextPage: Class<*>)
{
    val nextPg = Intent(this, nextPage)
    nextPg.putExtra(name, str)
}

fun Context.getStrFmPrevPg(name: String, prevPage: Class<*>) : String
{
    val prevPg = Intent(this, prevPage)
    return prevPg.getStringExtra(name).toString()
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

fun chkName(theName : String) : Boolean
{
    val nameRegEx = NAME_REGEX
    return (theName.matches((Regex(nameRegEx))))
}

fun isValidStudentAge(age: Int): Boolean {
    // You can define your validation rules here
    return age in MIN_STUD_AGE..MAX_STUD_AGE // Assuming a reasonable age range
}

fun isValidTutorAge(age: Int): Boolean {
    // You can define your validation rules here
    return age in MIN_TUTOR_AGE..MAX_TUTOR_AGE // Assuming a reasonable age range
}

fun isValidPhNo(phNo : Int) : Boolean{
    //Context.pr("PhNo chk")
    val regex = Regex(PHONE_REGEX)
   // return true
    return (regex.matches(phNo.toString()))
}

