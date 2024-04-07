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
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable;
class CommonProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCommonProfileBinding
    private lateinit var nextPage: Class<*>
    private var nextPage1: Class<*> = StudProfile::class.java
    private var nextPage2: Class<*> = TutorProfile::class.java
    private var userTypeVal = 0
    private lateinit var user: User
    private lateinit var currentUserEmail : String
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
       // val userTypeVal : Int = uType.toInt()
        setUpListenerWatchers()
        Ininui()
    }

    private fun Ininui()
    {
        binding.profBack.setOnClickListener{
            val intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
    }

    private fun setUpListenerWatchers() {
        // Add text change listeners to all EditText fields

        pr("Here 12")
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
                pr("Valid chked")
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
        val isValidFName = ( fName.isNotEmpty() && chkName(fName) )// Example validation logic
        val isValidLName = ( lName.isNotEmpty() && chkName(lName) )// Example validation logic
        val isValidAge = (age != null && isValidStudentAge(age))  //
        val isValidCity = city.isNotEmpty() // Example validation logic
        val isValidAddress = address.isNotEmpty() // Example validation logic

        if(isValidFName && isValidLName && isValidAge && isValidCity && isValidAddress)
        {
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
            val nextIntent = Intent(this, nextPage)
            nextIntent.putExtra("user", user)
            startActivity(nextIntent)

//val user1 = intent.getSerializableExtra("user") as? User

        } else if (2 == userTypeVal) {
            pr("Tutor user!")
            nextPage = nextPage2
        }
      //  pr("All text boxes validated!")
        val nextPg = Intent(this, nextPage)
        startActivity(nextPg)
        finish()
    }

    private fun addStudUser()
    {
        // Assuming you have already initialized FirebaseApp and FirebaseDatabase in your application
        // Step 1: Get a reference to your Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val usersRef = database.getReference("users")
        // Step 3: Store the User object in the database
                  // Generating a unique key for the user
            val userId = usersRef.push().key
            if (userId != null) {
                // Store the user object at the generated key
                setCurUserEmail()
                user.setTheEmail(currentUserEmail)
                user.setUserCat(userTypeVal)
                usersRef.child(userId).setValue(user)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                       // pr("User data saved successfully!")
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while saving user data
                        pr("Error saving user data: ${e.message}")
                    }
            } else {
                pr("Failed to generate a unique key for the user.")
            }


    }


    private fun setCurUserEmail()
    {
        // Step 1: Get the current user object from FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        // Step 2: Check if the current user is not null, then get the email
        currentUser?.let { user ->
            currentUserEmail = user.email.toString()
            if (currentUserEmail != null) {
              //  println("Current user's email: $currentUserEmail")
                // You can use the email here as needed, such as displaying it in your UI or storing it in a variable.
            } else {
              //  println("Current user's email is null")
            }
        } ?: run {
          //  println("No user is currently signed in")
        }
    }

    fun pr(msg: String) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show()
    }

}//End- class CommonProfile : AppCompatActivity()

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