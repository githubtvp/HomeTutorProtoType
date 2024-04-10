package com.example.login_page1

import java.io.Serializable

class UserModel1(
    var fName: String = "",
    var lName: String = "",
    var city: String = "",
    var address: String = "",
    var age: Int = 0
) : Serializable {
    constructor() : this("", "", "", "", 0)


}
