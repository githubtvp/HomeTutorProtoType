package com.example.login_page1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class StudProfile : AppCompatActivity() {

    private var userTypeVal = 0
    private lateinit var user: User
    private lateinit var currentUserEmail : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stud_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
// Inside your next activity
       user = intent.getSerializableExtra("user") as User // Assuming User is the class type
// Now you can use the user object
        pr("Stud Profile: ${user.email}")

    }


}