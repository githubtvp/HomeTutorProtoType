package com.example.login_page1


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.login_page1.databinding.ActivityCommonProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener


class CommonProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCommonProfileBinding
    private lateinit var nextPage: Class<*>
    private var npStudProf: Class<*> = StudProfile::class.java
    private var npTutorProf: Class<*> = TutorProfile::class.java
    private var npEditProf: Class<*> = EditProfile::class.java
    private var userTypeVal = 0
    private lateinit var stud: StudModel
    private lateinit var tutor: TutorModel
    private var EmailExists = false

    private var isValidFName = false;
    private var isValidLName = false;
    private var isValidCity = false;
    private var isValidAddress = false;
    private var isValidAge = false;
    private var isValidPhoneNo = false;


    private lateinit var currentUserEmail: String

    // Define the callback interface
    interface StringResultCallback {
        fun onResult(result: String)
    }

    // Define the callback interface
    interface BooleanResultCallback {
        fun onResult(result: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        val uType = intent.getStringExtra("userTypeVal").toString()
        if (uType != null) {
            userTypeVal = uType.toInt()
        } else {
            // Handle the case where "userTypeVal" is not set in the intent extras
        }
        binding.btnNext.isEnabled = false
//        var countryCodePicker = binding.countryCode
//        var phoneInput = binding.phoneNo
        setCurUserEmail()

        if (1 == userTypeVal) {
           // pr("Student user!")
            checkStudProfileExists() // and then setUpListenerWatchers
        } else if (2 == userTypeVal) {
            pr("Tutor user!")
            checkTutorProfileExists() // and then setUpListenerWatchers
        }
        Ininui()
    }//End - override fun onCreate(savedInstanceState: Bundle?)

    private fun Ininui() {
        binding.profBack.setOnClickListener {
            val intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
    }

    private fun setUpListenerWatchers2() {
        binding.firstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val fName = s.toString().trim()
                isValidFName =  false
                isValidFName = (fName.isNotEmpty() && chkName(fName))// Example validation logic
              //  validateAllInputs()
                if (isValidFName) {
                    binding.lastName.isEnabled = true
                } else {
                    binding.lastName.isEnabled = false
                    binding.age.isEnabled = false
                    binding.mobileNo.isEnabled = false
                    binding.address.isEnabled = false
                    binding.city.isEnabled = false
                    binding.btnNext.isEnabled = false
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        binding.lastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
                val lName = binding.lastName.text.toString().trim()
                isValidLName =  false
                isValidLName = (lName.isNotEmpty() && chkName(lName))// Example validation logic
                //validateAllInputs()
                if (isValidLName) {
                    binding.age.isEnabled = true
                } else {
                    binding.mobileNo.isEnabled = false
                    binding.address.isEnabled = false
                    binding.city.isEnabled = false
                    binding.btnNext.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        binding.age.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val ageTxt = binding.age.text.toString().trim()
                val age: Int? = ageTxt.toIntOrNull()
                isValidAge = false
                isValidAge = (age != null && isValidStudentAge(age)) // Example validation logic
                //validateAllInputs()
                if (isValidAge) {
                    //pr("here Mob")
                    binding.mobileNo.isEnabled = true
                }
                else {
                    binding.city.isEnabled = false
                    binding.address.isEnabled = false
                    binding.btnNext.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        binding.mobileNo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
              //  val phoneNoTxt = binding.mobileNo.text.toString().trim()
             //   val phoneNo: Int? = phoneNoTxt.toIntOrNull()
                val phoneNoTxt = binding.mobileNo.text.toString().trim()
                // Convert the phone number to integer safely
                val phoneNo: Int? = phoneNoTxt.toIntOrNull()
                isValidPhoneNo = false
                // Check if the phone number is valid and not null
                if (phoneNo != null) {
                  //  isValidPhoneNo = isValidPhNo(phoneNo)
                    binding.city.isEnabled = true
                }else {
                    binding.address.isEnabled = false
                    binding.btnNext.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        binding.city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
                val city = binding.city.text.toString().trim()
                isValidCity =  false
                isValidCity = (city.isNotEmpty())// Example validation logic
               // validateAllInputs()
                if (isValidCity) {
                    binding.address.isEnabled = true
                } else {
                    binding.btnNext.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        binding.address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
                val address = binding.address.text.toString().trim()
                isValidAddress = false
                isValidAddress = (address.isNotEmpty())// Example validation logic
               // validateAllInputs()
                if (isValidAddress) {
                    binding.btnNext.isEnabled = true
                    val fName = binding.firstName.text.toString().trim()
                    val lName = binding.lastName.text.toString().trim()
                    val city = binding.city.text.toString().trim()
                    val address = binding.address.text.toString().trim()
                    val ageTxt = binding.age.text.toString().trim()
                    val age: Int? = ageTxt.toIntOrNull()
                    val phoneNoTxt = binding.mobileNo.text.toString().trim()
                    val phoneNo: Long? = phoneNoTxt.toLongOrNull()
                    var phNo = 8296077914//phoneNo
                    stud = StudModel(fName, lName, city, address, currentUserEmail, phNo!!,  userTypeVal, age!!)
                    binding.btnNext.setOnClickListener { goToFullStudProfilePage() }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })
       // setFocusLost()
    }

    private fun goToFullStudProfilePage() {
      //  nextPage = npStudProf
        pr("goToHomePage + \n$nextPage")
        var intent = Intent(this, StudProfile::class.java)
        intent.putExtra("stud", stud)
        startActivity(intent)
        //finish()
    }

//    private fun setFocusLost() {
//        binding.btnNext.isEnabled = false
//        // Assuming editTexts is a list of EditText instances
//        val messages = listOf(
//            "Invalid First Name",
//            "Invalid Last Name",
//            "Invalid Age",
//            "Invalid City Name",
//            "Invalid Address"
//            // Add more messages for each EditText as needed
//        )
//        // Initialize the list of EditTexts
//        val editTexts =
//            listOf(binding.firstName, binding.lastName, binding.age, binding.city, binding.address)
//        for ((index, editText) in editTexts.withIndex()) {
//            val message = messages.getOrNull(index) ?: "Default message"
//            editText.setOnFocusChangeListener { view, hasFocus ->
//                if (!hasFocus) {
//                    // This block will be executed when the EditText loses focus
//                    val text = editText.text.toString()
//                    // Perform validation based on the type of EditText
//                    val isValid = when (editText) {
//                        // Add cases for each EditText requiring different validation
//                        binding.firstName ->(text.isNotEmpty() && chkName(text))
//                        binding.lastName -> (text.isNotEmpty() && chkName(text))
//                        binding.age -> (text.toIntOrNull() != null && isValidStudentAge(text.trim().toInt()))
//                        binding.city -> (text.isNotEmpty())
//                        binding.address -> (text.isNotEmpty())
//                        // Add more cases as needed
//                        else -> true // Default to true if no specific validation is needed
//                    }
//                    // Perform action based on validation result
//                    if (!isValid) {
//                        // Show a toast message or perform any other action to notify the user
//                        pr(message)
//                        editText.requestFocus()
//                    }
//                    binding.btnNext.isEnabled = isValid
//                    if (isValid) {
//                        binding.btnNext.isEnabled = true
//                        val fName = binding.firstName.text.toString().trim()
//                        val lName = binding.lastName.text.toString().trim()
//                        val city = binding.city.text.toString().trim()
//                        val address = binding.address.text.toString().trim()
//                        val ageTxt = binding.age.text.toString().trim()
//                        val age: Int? = ageTxt.toIntOrNull()
//                        // val phoneNoTxt = binding.phoneNo.text.toString().trim()
//                        //  val phoneNo: Int? = phoneNoTxt.toIntOrNull()
//                        var phNo = 8296077914 //phoneNo
//                      //  stud = StudModel(fName, lName, city, address, currentUserEmail, phNo!!,  userTypeVal, age!!)
//                        binding.btnNext.setOnClickListener { goToFullStudProfilePage() }
//                    }
//                }
//            }
//        }
//    }

    // Callback function that will be invoked when all validations pass
//    private fun onValidationSuccess() {
//        binding.btnNext.isEnabled = true
//        val fName = binding.firstName.text.toString().trim()
//        val lName = binding.lastName.text.toString().trim()
//        val city = binding.city.text.toString().trim()
//        val address = binding.address.text.toString().trim()
//        val ageTxt = binding.age.text.toString().trim()
//        val age: Int? = ageTxt.toIntOrNull()
//       // val phoneNoTxt = binding.phoneNo.text.toString().trim()
//      //  val phoneNo: Int? = phoneNoTxt.toIntOrNull()
//        var phNo = 8296077914 //phoneNo
//        binding.btnNext.isEnabled = true
//      //  stud = StudModel(fName, lName, city, address, currentUserEmail, phNo!!,  userTypeVal, age!!)
//
//        binding.btnNext.setOnClickListener { goToFullStudProfilePage() }
//    }

    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields
       // pr("LisWatcher")
        binding.firstName.addTextChangedListener(getTextWatcher)
        binding.lastName.addTextChangedListener(getTextWatcher)
        binding.age.addTextChangedListener(getTextWatcher)
        binding.city.addTextChangedListener(getTextWatcher)
        binding.address.addTextChangedListener(getTextWatcher)

      //  binding.phoneNo.addTextChangedListener(getTextWatcher)
        // Disable forward arrow button initially
        binding.btnNext.isEnabled = false
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
          //   pr("Here : afterTextChanged")
            // Validate all EditText fields
            val isValid = validateEntries()

            // Enable or disable the forward arrow button based on validation result
            binding.btnNext.isEnabled = isValid
          //  pr("Pre - Valid chked : $isValid")
            if (isValid) {
                pr("Valid chked")
                binding.btnNext.isEnabled = true
                //binding.btnnext.setOnClickListener { onFwdArrClickNextPage() }
                binding.btnNext.setOnClickListener { goToFullStudProfilePage() }
            }
        }
    }

    private fun validateEntries(): Boolean {
      //  pr("Here : validateEntries")
        val fName = binding.firstName.text.toString().trim()
        val lName = binding.lastName.text.toString().trim()
        val ageTxt = binding.age.text.toString().trim()
        val age: Int? = ageTxt.toIntOrNull()
        val city = binding.city.text.toString().trim()
        val address = binding.address.text.toString().trim()

     //   val phoneNoTxt = binding.phoneNo.text.toString().trim()
     //   val phoneNo: Int? = phoneNoTxt.toIntOrNull()

        // Perform validation for each EditText field
        var isValidFName =  false
        var isValidLName = false
        var isValidAge = false
        var isValidCity = false
        var isValidAddress = false

        isValidFName = (fName.isNotEmpty() && chkName(fName))// Example validation logic
        isValidLName = (lName.isNotEmpty() && chkName(lName))// Example validation logic
        isValidAge = (age != null && isValidStudentAge(age))  //
        isValidCity = city.isNotEmpty() // Example validation logic
        isValidAddress = address.isNotEmpty() // Example validation logic


        val isValidPhoneNo = true //(phoneNo != null && isValidPhNo(phoneNo))
      //  pr("Here : isValidPhoneNo : $isValidPhoneNo")
        if (isValidFName && isValidLName && isValidAge && isValidCity && isValidAddress && isValidPhoneNo  ) {
            pr("here in if")
            var phNo = 8296077914 //phoneNo
            binding.btnNext.isEnabled = true
        //    stud = StudModel(fName, lName, city, address, currentUserEmail, phNo!!,  userTypeVal, age!!)

//            if (1 == userTypeVal) {
//                pr("here in if")
//                stud = StudModel(fName, lName, city, address, currentUserEmail, phoneNo!!,  userTypeVal, age!!)
//            } else {
//                tutor =
//                    TutorModel(fName, lName, city, address, "email1", "s0", phoneNo!!, 0, age!!)
//            }

        }
        // Return true if all EditText fields are valid, otherwise false
        pr("isValidFName : $isValidFName")
        pr("isValidLName : $isValidLName")
        pr("isValidAge : $isValidAge")

        pr("isValidCity : $isValidCity")
        pr("isValidAddress : $isValidAddress")

        pr("isValidPhoneNo : $isValidPhoneNo")
        return (isValidFName && isValidLName && isValidAge   && isValidCity && isValidAddress && isValidPhoneNo)
    }


    private fun onFwdArrClickNextPage() {
        //pr("Here 12")
        if (1 == userTypeVal) {
            //   pr("Student user!")
            addStudUser()
        } else if (2 == userTypeVal) {
           // pr("Tutor user!")
            addTutorUser()
        }
    }



    private fun goToFullProfile() {
        pr("goToFullProfile")
        if (1 == userTypeVal) {
            pr("Student user!")
            nextPage = npStudProf
            //val user1 = intent.getSerializableExtra("user") as? User
            // val stud = intent.getSerializableExtra("Student")
            //  val stud = intent.getSerializableExtra("Student") as? stud
            var intent = Intent(this, nextPage)
            intent.putExtra("stud", stud)
            startActivity(intent)
            finish()
        } else if (2 == userTypeVal) {
            pr("Tutor user!")
            nextPage = npTutorProf
            var intent = Intent(this, nextPage)
            intent.putExtra("tutor", tutor)
            startActivity(intent)
            finish()
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
                stud.setTheEmail(currentUserEmail)
                stud.setUserType(userTypeVal)
              //  stud.setId(studId)
                studRef.child(studId).setValue(stud)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                        // pr("Student data saved successfully!")
                        goToFullProfile()
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

    private fun addTutorUser() {
        // Assuming you have already initialized FirebaseApp and FirebaseDatabase in your application
        // Step 1: Get a reference to your Firebase Realtime Database
        val db = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val tutorRef = db.getReference("Tutor")
        // Generate a unique key for the user
        getNextId { tutorId ->
            // If userId is null, then something went wrong, handle it
            if (tutorId != null) {
                // Store the user object at the generated key
                //   pr("here addStudUser A2 !!")
                // setCurUserEmail()
                tutor.setTheEmail(currentUserEmail)
                tutor.setUserType(userTypeVal)
                tutor.setId(tutorId)
                tutorRef.child(tutorId).setValue(tutor)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                        // pr("Student data saved successfully!")
                        goToFullProfile()
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
                        if (1 == userTypeVal) {
                            pr("Student user!")
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
                        } else if (2 == userTypeVal) {
                            pr("Tutor user!")
                            val tutorId = generateTutorKey(countData.TutorCnt + 1)
                            // Increment counter and update it in the database
                            //    pr("here getNextId 2 : increment counter!!")
                            countRef.child("TutorCnt").setValue(countData.TutorCnt + 1)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Call the callback function with the generated userId
                                        //  pr("Tutor count: ${countData.TutorCnt}")
                                        callback(tutorId)
                                    } else {
                                        pr("No count data available 1")
                                        callback(null) // Pass null to indicate failure
                                    }
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

    fun generateTutorKey(cnt: Int): String {
        return "t$cnt"
    }

    fun setCurUserEmail() {
        // Step 1: Get the current user object from FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        // Step 2: Check if the current user is not null, then get the email
        currentUser?.let { user ->
            currentUserEmail = user.email.toString()
        }
    }

    // Function to check for duplicates and create a new record if there are none
    fun checkStudProfileExists() {
        // Get a reference to the "Student" collection in the database
        val studRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Student")

        // Create a query to search for records with matching email
        val emailQuery: Query = studRef.orderByChild("email").equalTo(currentUserEmail)

        // Listener to check for duplicates based on email
        emailQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Print the dataSnapshot contents for debugging
                if (dataSnapshot.exists()) {
                    EmailExists = true
                    // pr("Email chkd Exists already")
                    // Proceed to Edit profile or other logic for existing record
                } else {
                    EmailExists = false
                   //   pr("Email does not exist.")
                    // Process create new profile
                    // setUpListenerWatchers()
                    setUpListenerWatchers2()
//                    pr("isValidFName : $isValidFName")
//                    pr("isValidLName : $isValidLName")
//                    pr("isValidCity : $isValidCity")
//                    pr("isValidAddress : $isValidAddress")
//                    pr("isValidAge : $isValidAge")
//                    validateAllInputs()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                pr("Database error: ${databaseError.message}")
            }
        })
    }

    // Function to check for duplicates and create a new record if there are none
    fun checkTutorProfileExists() {
        // Get a reference to the "Student" collection in the database
        val tutorRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Tutor")

        // Create a query to search for records with matching email
        val emailQuery: Query = tutorRef.orderByChild("email").equalTo(currentUserEmail)

        // Listener to check for duplicates based on email
        emailQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Print the dataSnapshot contents for debugging
                if (dataSnapshot.exists()) {
                    EmailExists = true
                    // pr("Email chkd Exists already")
                    // Proceed to Edit profile or other logic for existing record
                } else {
                    EmailExists = false
                    //  pr("Email does not exist.")
                    // Process create new profile
                   // setUpListenerWatchers()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                pr("Database error: ${databaseError.message}")
            }
        })
    }


    // Function to create a new record in the database
    fun createNewRecord(userData: UserModel) {
        // Get a reference to the "users" collection in the database
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Generate a new key for the new record
        val newRecordKey = usersRef.push().key

        // Set the user data at the new record key
        if (newRecordKey != null) {
            usersRef.child(newRecordKey).setValue(userData)
                .addOnSuccessListener {
                    // Success handling
                    println("New record created successfully.")
                }
                .addOnFailureListener { error ->
                    // Failure handling
                    println("Failed to create new record: ${error.message}")
                }
        }
    }


}//End- class CommonProfile : AppCompatActivity()


/*
 // Function to check for duplicates and create a new record if there are none
    fun checkProfileExists() {
        // Get a reference to the "Student" collection in the database
        val studRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Student")

        // Create a query to search for records with matching email
        val emailQuery: Query = studRef.orderByChild("email").equalTo(currentUserEmail)

        // Print the currentUserEmail value for debugging
        pr("currentUserEmail: $currentUserEmail")
        // Create a query to search for records with matching type
     //   val typeQuery: Query = studRef.orderByChild("type").equalTo(userTypeVal.toDouble())

        // Listener to check for duplicates based on email
        emailQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Print the dataSnapshot contents for debugging
               // pr("DScon\n: $dataSnapshot")

                if (dataSnapshot.exists()) {

                    dataSnapshot.children.forEach { childSnapshot ->
                        // Print the key and the value of the child
                      //  pr("Key: ${childSnapshot.key}, Value: ${childSnapshot.value}")
                        // Extract the "email" field from the child node and print it
                        val email = childSnapshot.child("email").getValue(String::class.java)
                        pr("Em: $email")
                    }

                    // If there are matching records for the email
                    pr("Duplicate record found based on email. Record not created.")

                    EmailExists = true
                } else {
                    // Proceed to check type duplication
                  //  pr("Email does not exist.")
                    EmailExists = false // return checkTypeDuplicate(typeQuery)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                pr("Database error: ${databaseError.message}")
            }
        })
    }

 */
//    private fun getNextId(callback: (String?) -> Unit) {
//        val db = FirebaseDatabase.getInstance()
//        val usersRef = db.getReference("users")
//      //  pr("$usersRef - getNextId B1 : ")
//        val counterRef = db.getReference("user-count")
//     //   pr("$counterRef - getNextId B1 : ")
//        // Retrieve the current counter value and generate a new user key
//        val cnt = "count"
//        counterRef.child(cnt).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val userCounter = dataSnapshot.getValue(Int::class.java) ?: 0
//                val userId = generateUserKey(userCounter + 1)
//                // Increment counter and update it in the database
//                pr("here getNextId B2 : increment counter!!")
//                counterRef.setValue(userCounter + 1).addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Call the callback function with the generated userId
//                        callback(userId)
//                    } else {
//                        // Handle error
//                        callback(null) // Pass null to indicate failure
//                    }
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                pr("Database error: ${databaseError.message}")
//                callback(null) // Pass null to indicate failure
//            }
//        })
//    }

//    private fun getNextId(callback: (String?) -> Unit) {
//        val db = FirebaseDatabase.getInstance()
//      //  val usersRef = db.getReference("users")
//        val counterRef = db.getReference("usercounter")
//        pr("getNextId 1 : increment counter!!")
//        // Retrieve the current counter value and generate a new user key
//        val counterRefListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val userCounter = dataSnapshot.getValue(Int::class.java) ?: 0
//                val userId = generateUserKey(userCounter + 1)
//                // Increment counter and update it in the database
//                pr("here getNextId 2 : increment counter!!")
//                counterRef.setValue(userCounter + 1).addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Call the callback function with the generated userId
//                        callback(userId)
//                    } else {
//                        // Handle error
//                        callback(null) // Pass null to indicate failure
//                    }
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                pr("Database error: ${databaseError.message}")
//               // callback(null) // Pass null to indicate failure
//            }
//        }
//        counterRef.addValueEventListener(counterRefListener)
//    }
/*
       private fun getNextId(callback: (String) -> Unit) {
       val db = FirebaseDatabase.getInstance()
       val usersRef = db.getReference("users")
       val counterRef = db.getReference("usercounter")

       counterRef.addListenerForSingleValueEvent(object : ValueEventListener {
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               val userCounter = dataSnapshot.getValue(Int::class.java) ?: 0
               val userId = generateUserKey(userCounter + 1)

               // Increment counter and update it in the database
               counterRef.setValue(userCounter + 1).addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       // Call the callback function with the generated userId
                       callback(userId)
                   } else {
                       // Handle error
                   }
               }
           }

           override fun onCancelled(databaseError: DatabaseError) {
               // Handle error
           }
       })
   }




        */


/*
val usersRef = database.getReference("users")
val counterRef = database.getReference("userCounter")

// Function to generate user keys with the format "u1", "u2", "u3", ...
fun generateUserKey(userCounter: Int): String {
    return "u$userCounter"
}

// Retrieve the current counter value and generate a new user key
counterRef.addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val userCounter = dataSnapshot.getValue(Int::class.java) ?: 0
        val userId = generateUserKey(userCounter + 1)

        // Increment counter and update it in the database
        counterRef.setValue(userCounter + 1)

        // Example of storing a user with the generated key
        val user = User("John", "Doe", "New York", "123 Street", 25)
        usersRef.child(userId).setValue(user)
    }

    override fun onCancelled(databaseError: DatabaseError) {
        // Handle error
    }
})

 */