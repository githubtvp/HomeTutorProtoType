package com.example.login_page1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TutorProfile : AppCompatActivity() {

    private lateinit var tutor: TutorModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve the serializable extra from the intent
        //val stud = intent.getSerializableExtra("stud")
        tutor = intent.getSerializableExtra("tutor") as TutorModel // Assuming User is the class type
// Check if the retrieved object is not null and can be cast to the expected type
        if (tutor is TutorModel) {
            // Now you can safely use the stud object
            pr("Tutor email: ${tutor.email}")
            pr("Tutor age: ${tutor.age}")
            pr("Tutor addre: ${tutor.address}")
        } else {
            // Handle the case where the retrieved object is not of the expected type or is null
            pr("Error: Retrieved object is not of type StudModel or is null")
        }


    }
}