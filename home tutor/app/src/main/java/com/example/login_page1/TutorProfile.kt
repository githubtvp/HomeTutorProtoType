package com.example.login_page1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_page1.databinding.ActivityStudProfileBinding
import com.example.login_page1.databinding.ActivityTutorProfileBinding

class TutorProfile : AppCompatActivity() {

    private lateinit var tutor: ComModel
    private lateinit var binding: ActivityTutorProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setContentView(R.layout.activity_tutor_profile)

        // Retrieve the serializable extra from the intent
        //val stud = intent.getSerializableExtra("stud")
        tutor = intent.getSerializableExtra("tutor") as ComModel // Assuming User is the class type
// Check if the retrieved object is not null and can be cast to the expected type
        if (tutor is ComModel) {
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