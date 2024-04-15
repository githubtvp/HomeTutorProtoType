package com.example.login_page1

import Student
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_page1.databinding.ActivityStudProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StudProfile : AppCompatActivity() {

    private lateinit var binding: ActivityStudProfileBinding
    private lateinit var nextPage: Class<*>
    private lateinit var studRecd: StudModel
    private lateinit var theStudent: Student
    private var npHomePage: Class<*> = Home_page::class.java

    private var isValidClass = false;
    private var isValidSchoolName = false;
    private var isValidParentName = false;
    private var isValidParentPhNo = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSubmitProfile.isEnabled = false

        // Retrieve the serializable extra from the intent
        //val stud = intent.getSerializableExtra("stud")
        studRecd =
            intent.getSerializableExtra("stud") as StudModel // Assuming User is the class type
//// Check if the retrieved object is not null and can be cast to the expected type
//        if (studRecd is StudModel) {
//            // Now you can safely use the stud object
//            pr("Stud email: ${studRecd.email}")
//            pr("Stud age: ${studRecd.age}")
//            pr("Stud addre: ${studRecd.address}")
//        } else {
//            // Handle the case where the retrieved object is not of the expected type or is null
//            pr("Error: Retrieved object is not of type StudModel or is null")
//        }
    }//End - override fun onCreate(savedInstanceState: Bundle?)

    private fun setUpListenerWatchers2() {
        binding.classStd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val cls = s.toString().trim()
                isValidClass = false
                isValidClass = (cls.isNotEmpty())// Example validation logic
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
                val schName = binding.schoolName.text.toString().trim()
                isValidSchoolName = false
                isValidSchoolName = (schName.isNotEmpty())// Example validation logic
                if (isValidSchoolName) {
                    binding.parentName.isEnabled = true
                } else {
                    binding.parentPhno.isEnabled = false
                    binding.btnSubmitProfile.isEnabled = false
                }


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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val phoneNoTxt = binding.parentPhno.text.toString().trim()
                // Convert the phone number to integer safely
                val phoneNo: Int? = phoneNoTxt.toIntOrNull()
                isValidParentPhNo = false
                // Check if the phone number is valid and not null
                if (phoneNo != null) {
                    //  isValidPhoneNo = isValidPhNo(phoneNo)
                    binding.btnSubmitProfile.isEnabled = true
                    val classStd = binding.classStd.text.toString().trim()
                    val schName = binding.schoolName.text.toString().trim()
                    val parName = binding.parentName.text.toString().trim()
                    val parPhoneNoTxt = binding.parentPhno.text.toString().trim()
                    val parPhoneNo: Int = parPhoneNoTxt.toInt()

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
        })//End - binding.parentPhno.addTextChangedListener(object : TextWatcher
    }//End - private fun setUpListenerWatchers2()


    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields
        // pr("LisWatcher")
        binding.classStd.addTextChangedListener(getTextWatcher)
        binding.schoolName.addTextChangedListener(getTextWatcher)
        binding.parentName.addTextChangedListener(getTextWatcher)
        binding.parentPhno.addTextChangedListener(getTextWatcher)
        // Disable forward arrow button initially
        binding.btnSubmitProfile.isEnabled = false
    }

    //create a TextWatcher object to attach to each EditText object
    private val getTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No implementation needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No implementation needed
        }

        override fun afterTextChanged(s: Editable?) {
            //  pr("Here 13")
            // Validate all EditText fields
            val isValid = validateEntries()
            // Enable or disable the forward arrow button based on validation result
            binding.btnSubmitProfile.isEnabled = isValid
            if (isValid) {
                // pr("Valid chked")
                binding.btnSubmitProfile.isEnabled = true
                binding.btnSubmitProfile.setOnClickListener { addStudUser() }
            }
        }
    }

    private fun addStudUser() {
        // Assuming you have already initialized FirebaseApp and FirebaseDatabase in your application
        // Step 1: Get a reference to your Firebase Realtime Database
        val db = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val studRef = db.getReference("Student")
        //     pr("addStudUser A1 !!")
        // Generate a unique key for the user
        getNextId { studId ->
            // If userId is null, then something went wrong, handle it
            if (studId != null) {
                // Store the user object at the generated key
                //   pr("here addStudUser A2 !!")
                // setCurUserEmail()
                theStudent.setId(studId)
                studRef.child(studId).setValue(theStudent)
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

    private fun goToHomePage() {
        pr("goToHomePage")
        nextPage = npHomePage
        var intent = Intent(this, nextPage)
        startActivity(intent)
        finish()
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
                        val studId = generateStudentKey(countData.StudCnt + 1)
                        // Increment counter and update it in the database
                        //    pr("here getNextId 2 : increment counter!!")
                        countRef.child("StudCnt").setValue(countData.StudCnt + 1)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Call the callback function with the generated userId
                                    //     pr("Student count: ${countData.StudCnt}")
                                    callback(studId)
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

    fun generateStudentKey(cnt: Int): String {
        //  pr("NStudKey: " + "s"+ cnt)
        return "s$cnt"
    }


    private fun validateEntries(): Boolean {
        //  pr("Here 14")
        val classStud = binding.classStd.text.toString().trim()
        val schName = binding.schoolName.text.toString().trim()
        val parName = binding.parentName.text.toString().trim()
        val parPhoneNoTxt = binding.parentPhno.text.toString().trim()
        val parPhoneNo: Int = parPhoneNoTxt.toInt()

        // Perform validation for each EditText field
        val isValidClass = (classStud.isNotEmpty())// Example validation logic
        val isValidschName = (schName.isNotEmpty())// Example validation logic
        val isValidparName = (parName.isNotEmpty() && chkName(parName))  //
        val isValidPhoneNo = (parPhoneNo != null && isValidPhNo(parPhoneNo))
        if (isValidClass && isValidschName && isValidparName && isValidPhoneNo) {
            theStudent = Student(
                stud = studRecd,
                studId = "",
                classStd = classStud,
                schoolName = schName,
                parentName = parName,
                parentPhno = parPhoneNo
            )
        }
        // Return true if all EditText fields are valid, otherwise false
        return isValidClass && isValidschName && isValidparName && isValidPhoneNo
    }//End - private fun validateEntries(): Boolean


}//End - class StudProfile : AppCompatActivity()

/*

fun deleteStudentRecord(studentId: String) {
    // Step 1: Get a reference to the "Student" collection in the database
    val database = FirebaseDatabase.getInstance()
    val studentRef = database.getReference("Student")

    // Step 2: Get a reference to the specific student record you want to delete
    val studentRecordRef = studentRef.child(studentId)

    // Step 3: Delete the specific student record
    studentRecordRef.removeValue()
        .addOnSuccessListener {
            // The record was deleted successfully
            println("Student record with ID '$studentId' deleted successfully.")
        }
        .addOnFailureListener { error ->
            // Handle any errors that occurred during deletion
            println("Failed to delete student record with ID '$studentId': ${error.message}")
        }
}

 */