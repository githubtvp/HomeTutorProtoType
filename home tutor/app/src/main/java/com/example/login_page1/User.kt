package com.example.login_page1

import java.io.Serializable

class User(
    var fName: String,
    var lName: String,
    var city: String,
    var address: String,
    var age: Int
) : Serializable {
    constructor() : this("", "", "", "", 0)

    var email: String = ""

    // Setter method for email
    fun setTheEmail(em: String) {
        this.email = em
    }

    var userType: Int = 0

    // Setter method for userType
    fun setUserCat(uType: Int) {
        this.userType = uType
    }
}
