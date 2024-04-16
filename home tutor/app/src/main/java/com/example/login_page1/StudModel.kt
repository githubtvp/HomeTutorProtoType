package com.example.login_page1

import java.io.Serializable

open class StudModel(
    var fName: String = "",
    var lName: String = "",
    var city: String = "",
    var address: String = "",
    var email: String = "",
    var phoneNo: Long = 0,
    var type: Int = 0,
    var age: Int = 0

) : Serializable {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        0

    )

    // Setter method for email
    fun setTheEmail(em: String) {
        this.email = em
    }

   // var userType: Int = 0

    // Setter method for userType
    fun setUserType(uType: Int) {
        this.type = uType
    }
}

