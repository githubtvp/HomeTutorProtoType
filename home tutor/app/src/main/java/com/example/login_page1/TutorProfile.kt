package com.example.login_page1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_page1.databinding.ActivityTutorProfileBinding

class TutorProfile : AppCompatActivity() {

    private lateinit var binding: ActivityTutorProfileBinding
    private lateinit var tutorRecd: ComModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Retrieve the serializable extra from the intent
        //val stud = intent.getSerializableExtra("stud")
     //   tutorRecd = intent.getSerializableExtra("tutor") as ComModel // Assuming User is the class type

// Check if the retrieved object is not null and can be cast to the expected type
//        if (tutorRecd is ComModel) {
//            // Now you can safely use the stud object
//            pr("Tutor email: ${tutorRecd.email}")
//            pr("Tutor age: ${tutorRecd.age}")
//            pr("Tutor addre: ${tutorRecd.address}")
//        } else {
//            // Handle the case where the retrieved object is not of the expected type or is null
//            pr("Error: Retrieved object is not of type StudModel or is null")
//        }
    }
}