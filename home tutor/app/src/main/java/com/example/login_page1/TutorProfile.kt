package com.example.login_page1

import Tutor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityTutorProfileBinding

class TutorProfile : AppCompatActivity() {

    private lateinit var binding: ActivityTutorProfileBinding
    private lateinit var tutorRecd: ComModel
    private lateinit var theTutor: Tutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_tutor_profile)
        binding = ActivityTutorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Retrieve the serializable extra from the intent
        //val stud = intent.getSerializableExtra("stud")
        tutorRecd =
            intent.getSerializableExtra("tutor") as ComModel // Assuming User is the class type

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

        theTutor = Tutor(
            tutor = tutorRecd,
            tutorId = "",
            qualification = "",
            experience = "",
            charges = 0.0,
           splAchieve = "",
            abtYourself = ""
        )
        // Set up OnCheckedChangeListener for each checkbox inside onCreate
        binding.checkboxCBSE.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            theTutor.boards.add("CBSE")
            pr("CBSE checked status: $isChecked")
        }

    }//End - override fun onCreate(savedInstanceState: Bundle?)


    private fun setUpListenerWatchers() {


    }


}///End - class TutorProfile : AppCompatActivity(
