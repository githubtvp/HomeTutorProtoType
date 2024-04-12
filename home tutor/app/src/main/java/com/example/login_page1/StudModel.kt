package com.example.login_page1

import java.io.Serializable

class StudModel(
    var fName: String = "",
    var lName: String = "",
    var city: String = "",
    var address: String = "",
    var email: String = "",
    var type: Int =0,
    var age: Int = 0,
    var id: Int = 0
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
}
