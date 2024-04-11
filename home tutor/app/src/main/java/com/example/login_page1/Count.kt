package com.example.login_page1
import java.io.Serializable

class Count(
    var StudCnt: Int,
    var TutorCnt: Int
) : Serializable {
    constructor() : this(0, 0)
}
