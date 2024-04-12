package com.example.login_page1


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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
    private var nextPage1: Class<*> = StudProfile::class.java
    private var nextPage2: Class<*> = TutorProfile::class.java
    private var userTypeVal = 0
    private lateinit var user: User
    private var EmailExists = false
    private var UserTypeMatches = false

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
        /*
        Check email and type exists alreay?
        if exists ... go to edit profile else
        proceed to add new student/tutor
         */
        setCurUserEmail()
        checkProfileExists()

        if(EmailExists == false)
        {
            //proceed to Edit profile
            pr("Email chkd Exists already")
        }
        else
        {
            //process create new profile
            setUpListenerWatchers()
        }
        // val userTypeVal : Int = uType.toInt()

        Ininui()
    }

    private fun Ininui() {
        binding.profBack.setOnClickListener {
            val intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
    }

    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields
        binding.firstname.addTextChangedListener(getTextWatcher)
        binding.lastname.addTextChangedListener(getTextWatcher)
        binding.age.addTextChangedListener(getTextWatcher)
        binding.city.addTextChangedListener(getTextWatcher)
        binding.Address.addTextChangedListener(getTextWatcher)
        // Disable forward arrow button initially
        binding.btnnext.isEnabled = false
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
            binding.btnnext.isEnabled = isValid
            if (isValid) {
                // pr("Valid chked")
                binding.btnnext.isEnabled = true
                binding.btnnext.setOnClickListener { onFwdArrClickNextPage() }
            }

        }
    }

    private fun validateEntries(): Boolean {
        //  pr("Here 14")
        val fName = binding.firstname.text.toString().trim()
        val lName = binding.lastname.text.toString().trim()
        val ageTxt = binding.age.text.toString().trim()
        val age: Int? = ageTxt.toIntOrNull()

        val city = binding.city.text.toString().trim()
        val address = binding.Address.text.toString().trim()

        // Perform validation for each EditText field
        val isValidFName = (fName.isNotEmpty() && chkName(fName))// Example validation logic
        val isValidLName = (lName.isNotEmpty() && chkName(lName))// Example validation logic
        val isValidAge = (age != null && isValidStudentAge(age))  //
        val isValidCity = city.isNotEmpty() // Example validation logic
        val isValidAddress = address.isNotEmpty() // Example validation logic

        if (isValidFName && isValidLName && isValidAge && isValidCity && isValidAddress) {
            user = User(fName, lName, city, address, age!!)
        }
        // Return true if all EditText fields are valid, otherwise false
        return isValidFName && isValidLName && isValidAge && isValidCity && isValidAddress
    }

    private fun onFwdArrClickNextPage() {
        //pr("Here 12")
        if (1 == userTypeVal) {
            pr("Student user!")
            nextPage = nextPage1
            addStudUser()
//val user1 = intent.getSerializableExtra("user") as? User
            var intent = Intent(this, nextPage)
            intent.putExtra("user", user)
            startActivity(intent)

        } else if (2 == userTypeVal) {
            pr("Tutor user!")
            nextPage = nextPage2
        }
        //  pr("All text boxes validated!")
        val nextPg = Intent(this, nextPage)
        //  startActivity(nextPg)
        finish()
    }

    private fun addStudUser() {
        // Assuming you have already initialized FirebaseApp and FirebaseDatabase in your application
        // Step 1: Get a reference to your Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val usersRef = database.getReference("users")
        //    pr("addStudUser A1 !!")
        // Generate a unique key for the user
        getNextId { userId ->
            // If userId is null, then something went wrong, handle it
            if (userId != null) {
                // Store the user object at the generated key
                pr("here addStudUser A2 !!")
                setCurUserEmail()
                user.setTheEmail(currentUserEmail)
                user.setUserCat(userTypeVal)

                usersRef.child(userId).setValue(user)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                        pr("User data saved successfully!")
                        // Here you can navigate to the next activity or perform any other action
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while saving user data
                        pr("Error saving user data: ${e.message}")
                    }

            } else {
                pr("Failed to generate a unique key for the user.")
            }
        }
    }

    private fun getNextId(callback: (String?) -> Unit) {
        val db = FirebaseDatabase.getInstance()
        val countRef = db.getReference("count")
        // Add a ValueEventListener to retrieve the data from the "count" collection
        countRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //   pr("dataSnapshot.exists()")
                    // Retrieve the Count model from the data snapshot
                    val countData = dataSnapshot.getValue(Count::class.java)
                    // Check if countData is not null
                    if (countData != null) {
                        //  val userId = countData?.let { generateStudentKey(it.StudCnt) }
                        val userId = generateStudentKey(countData.StudCnt)
                        // Increment counter and update it in the database
                        //  pr("here getNextId 2 : increment counter!!")
                        countRef.child("StudCnt").setValue(countData.StudCnt + 1)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
//                        // Call the callback function with the generated userId
                                    pr("Student count: ${countData.StudCnt}")
                                    //  pr("Tutor count: ${countData.TutorCnt}")
                                    callback(userId)
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

        fun pr(msg: String) {
            Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show()
        }

    fun setEmailExists()
    {
       // onResult(EmailExists: Boolean)
    }
    // Function to check for duplicates and create a new record if there are none
    fun checkProfileExists() {
        // Get a reference to the "users" collection in the database
        val studRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Student")

        // Create a query to search for records with matching email
        val emailQuery: Query = studRef.orderByChild("email").equalTo(currentUserEmail)

        // Create a query to search for records with matching type
        val typeQuery: Query = studRef.orderByChild("type").equalTo(userTypeVal.toDouble())

        // Listener to check for duplicates based on email
        emailQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If there are matching records for the email
                    pr("Duplicate record found based on email. Record not created.")
                    EmailExists = true
                } else {
                    // Proceed to check type duplication
                    EmailExists = false // return checkTypeDuplicate(typeQuery)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                pr("Database error: ${databaseError.message}")
            }
        })
    }

    // Function to check for duplicates based on type
    fun checkTypeDuplicate(typeQuery : Query) {
        // Listener to check for duplicates based on type
        typeQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If there are matching records for the type
                    pr("Duplicate record found based on type. Record not created.")
                } else {
                    // No duplicates found, create the new record
                  //  createNewRecord(userData)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                println("Database error: ${databaseError.message}")
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