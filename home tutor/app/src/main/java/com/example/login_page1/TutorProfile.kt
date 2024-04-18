package com.example.login_page1

import Student
import Tutor
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityTutorProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TutorProfile : AppCompatActivity() {

    private lateinit var binding: ActivityTutorProfileBinding
    private lateinit var nextPage: Class<*>
    private lateinit var tutorRecd: ComModel
    private lateinit var theTutor: Tutor
    private var npHomePage: Class<*> = Home_page::class.java
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
        binding.classStd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val clasTxt = binding.classStd.text.toString().trim()
                val cls: Int? = clasTxt.toIntOrNull()
                isValidClass = false
                isValidClass = (cls !=null)// Example validation logic
                //  validateAllInputs()
                if (isValidClass) {
                    binding.schoolName.isEnabled = true
                } else {
                    binding.parentName.isEnabled = false
                    binding.parentPhno.isEnabled = false
                    binding.btnSubmitProfile.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })//End - binding.classStd.addTextChangedListener(object : TextWatcher

        binding.schoolName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
                val schoolName = binding.schoolName.text.toString().trim()
                isValidSchoolName = false
                isValidSchoolName = (schoolName.isNotEmpty())// Example validation logic
                if (isValidSchoolName) {
                    binding.parentName.isEnabled = true
                } else {
                    binding.parentPhno.isEnabled = false
                    binding.btnSubmitProfile.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })///End - binding.schoolName.addTextChangedListener(object : TextWatcher

        binding.parentName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val parName = binding.parentName.text.toString().trim()
                isValidParentName = false
                isValidParentName = (parName.isNotEmpty()) // Example validation logic
                if (isValidParentName) {
                    binding.parentPhno.isEnabled = true
                } else {
                    binding.btnSubmitProfile.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })//End - binding.parentName.addTextChangedListener(object : TextWatcher

        binding.parentPhno.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val phoneNoTxt = binding.parentPhno.text.toString().trim()
                // Convert the phone number to integer safely
                val parPhoneNo: Long = phoneNoTxt.toLong()
                isValidParentPhNo = false
                // Check if the phone number is valid and not null
                if (parPhoneNo != null) {
                    pr("Here : StudProf")
                    //  isValidPhoneNo = isValidPhNo(phoneNo)
                    binding.btnSubmitProfile.isEnabled = true
                    val classStd = binding.classStd.text.toString().trim()
                    val schName = binding.schoolName.text.toString().trim()
                    val parName = binding.parentName.text.toString().trim()
                    //  val parPhoneNoTxt = binding.parentPhno.text.toString().trim()
                    //  val parPhoneNo: Long = parPhoneNoTxt.toLong()

                    theStudent = Student(
                        stud = studRecd,
                        studId = "",
                        classStd = classStd,
                        schoolName = schName,
                        parentName = parName,
                        parentPhno = parPhoneNo
                    )
                    binding.btnSubmitProfile.setOnClickListener { addStudUser() }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })//End - binding.parentPhno.addTextChangedListener(object : TextWatcher
    }//End - private fun setUpListenerWatchers2()

    private fun addStudUser() {
        // Assuming you have already initialized FirebaseApp and FirebaseDatabase in your application
        // Step 1: Get a reference to your Firebase Realtime Database
        val db = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val tutorRef = db.getReference("Tutor")
        //     pr("addStudUser A1 !!")
        // Generate a unique key for the user
        getNextId { tutorId ->
            // If userId is null, then something went wrong, handle it
            if (tutorId != null) {
                // Store the user object at the generated key
                //   pr("here addStudUser A2 !!")
                // setCurUserEmail()
                theTutor.setId(tutorId)
                tutorRef.child(tutorId).setValue(theTutor)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                        // pr("Student data saved successfully!")
                        goToHomePage()
                        // Here you can navigate to the next activity or perform any other action
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while saving user data
                        pr("Error saving Student data: ${e.message}")
                    }

            } else {
                pr("Failed to generate a unique key for the user.")
            }
        }
    }

    private fun getNextId(callback: (String?) -> Unit) {
        //  pr("getNextId")
        val db = FirebaseDatabase.getInstance()
        val countRef = db.getReference("count")
        // Add a ValueEventListener to retrieve the data from the "count" collection
        countRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //     pr("dataSnapshot.exists()")
                    // Retrieve the Count model from the data snapshot
                    val countData = dataSnapshot.getValue(Count::class.java)
                    // Check if countData is not null
                    if (countData != null) {
                        //    pr("Student user!")
                        val tutorId = generateTutorKey(countData.TutorCnt + 1)
                        // Increment counter and update it in the database
                        //    pr("here getNextId 2 : increment counter!!")
                        countRef.child("TutorCnt").setValue(countData.TutorCnt + 1)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Call the callback function with the generated userId
                                    //     pr("Tutor count: ${countData.TutorCnt}")
                                    callback(tutorId)
                                } else {
                                    pr("No count data available 1")
                                    callback(null) // Pass null to indicate failure
                                }
                            }

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                pr("Database error: ${databaseError.message}")
            }
        })
    }//End - private fun getNextId(callback: (String?) -> Unit)

    fun generateTutorKey(cnt: Int): String {
        //  pr("NStudKey: " + "s"+ cnt)
        return "t$cnt"
    }

    private fun goToHomePage() {
        pr("goToHomePage")
        nextPage = npHomePage
        var intent = Intent(this, nextPage)
        startActivity(intent)
        finish()
    }


}///End - class TutorProfile : AppCompatActivity(
