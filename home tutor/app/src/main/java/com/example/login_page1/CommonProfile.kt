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

    //  private lateinit var stud: StudModel
    private lateinit var com: ComModel
    //   private lateinit var tutor: TutorModel
    //   private var EmailExists = false

    private var isValidFName = false;
    private var isValidLName = false;
    private var isValidCity = false;
    private var isValidAddress = false;
    private var isValidAge = false;
    private var isValidPhoneNo = false;


    private lateinit var currentUserEmail: String
    private lateinit var comRef: DatabaseReference
    private lateinit var emailQuery: Query

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
        binding.lastName.isEnabled = false
        binding.age.isEnabled = false
        binding.mobileNo.isEnabled = false
        binding.address.isEnabled = false
        binding.city.isEnabled = false
        binding.btnNext.isEnabled = false
        initializeCom()
        setCurUserEmail()
        checkProfileExists()
        Ininui()
    }//End - override fun onCreate(savedInstanceState: Bundle?)


    private fun initializeCom()
    {
        // Make sure to initialize `com` with appropriate arguments
        val initialFName = ""  // Initial first name (e.g., empty string)
        val initialLName = ""  // Initial last name
        val initialCity = ""   // Initial city
        val initialAddress = ""  // Initial address
        val initialEmail = ""  // Initial email
        val initialPhoneNo: Long = 0L  // Initial phone number
        val initialType = 1  // Initial user type
        val initialAge = 0   // Initial age

        com = ComModel(
            initialFName,
            initialLName,
            initialCity,
            initialAddress,
            initialEmail,
            initialPhoneNo,
            initialType,
            initialAge
        )

    }

    private fun Ininui() {
        binding.profBack.setOnClickListener {
            val intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
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
    fun checkProfileExists() {
        // Get a reference to the "Student" collection in the database

        //  val studRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Student")

        // Create a query to search for records with matching email
        //   val emailQuery: Query = studRef.orderByChild("email").equalTo(currentUserEmail)

        setUpComRefandEmailQuery()
        // Listener to check for duplicates based on email
        emailQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Print the dataSnapshot contents for debugging
                if (dataSnapshot.exists()) {
                    //  EmailExists = true
                    pr("Email Exists already")
                    // Proceed to Edit profile or other logic for existing record
                } else {
                    //   EmailExists = false
                  //  pr("Email Does not")
                    setUpListenerWatchers()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                pr("Database error: ${databaseError.message}")
            }
        })
    }

    fun setUpComRefandEmailQuery() {
        if (1 == userTypeVal) {
            // Get a reference to the "Student" collection in the database
            comRef = FirebaseDatabase.getInstance().getReference("Student")
        } else if (2 == userTypeVal) {
            // pr("Tutor user TVP!")
            // Get a reference to the "Tutor" collection in the database
            comRef = FirebaseDatabase.getInstance().getReference("Tutor")
        }
        // Create a query to search for records with matching email
        emailQuery = comRef.orderByChild("email").equalTo(currentUserEmail)
    }

    private fun setUpListenerWatchers() {
        binding.firstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
                val fName = s.toString().trim()
                // pr("Here in listener 1")
                isValidFName = false
                isValidFName = (fName.isNotEmpty() && chkName(fName))// Example validation logic
                if (isValidFName) {
                    com.fName = fName
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
                isValidLName = false
                isValidLName = (lName.isNotEmpty() && chkName(lName))// Example validation logic
                //validateAllInputs()
                if (isValidLName) {
                    com.lName = lName
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
                if (1 == userTypeVal) {
                    isValidAge = (age != null && isValidStudentAge(age)) // Example validation logic
                } else if (2 == userTypeVal) {
                    isValidAge = (age != null && isValidTutorAge(age)) // Example validation logic
                }

                if (isValidAge) {
                    //pr("here Mob")
                    if (age != null) {
                        com.age = age
                      //  pr("Com-age : " + com.age)
                    } else {
                        pr("Com-InvalidAge")
                    }
                    binding.mobileNo.isEnabled = true
                } else {
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
                val phoneNo: Long? = phoneNoTxt.toLongOrNull()
                isValidPhoneNo = false
                // Check if the phone number is valid and not null
                isValidPhoneNo = (phoneNo != null && isValidPhNo(phoneNo))
                if (isValidPhoneNo) {
                    //  isValidPhoneNo = isValidPhNo(phoneNo)
                    if(phoneNo != null)
                    {
                        com.phoneNo = phoneNo
                       // pr("Com-phone : " + com.phoneNo)
                    }
                    else
                    {
                        pr("Com-InvalidPhoneNo")
                    }
                    binding.city.isEnabled = true
                } else {
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
                isValidCity = false
                isValidCity = (city.isNotEmpty())// Example validation logic
                // validateAllInputs()
                if (isValidCity) {
                    com.city = city
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
                //   pr("here 1")
                val address = binding.address.text.toString().trim()
                isValidAddress = false
                isValidAddress = (address.isNotEmpty())// Example validation logic
                if (isValidAddress) {
                    com.address = address
                    binding.btnNext.isEnabled = true
//                    val fName = binding.firstName.text.toString().trim()
//                    val lName = binding.lastName.text.toString().trim()
//                    val city = binding.city.text.toString().trim()
//                    val address = binding.address.text.toString().trim()
//                    val ageTxt = binding.age.text.toString().trim()
//                    val age: Int? = ageTxt.toIntOrNull()
//                    val phoneNoTxt = binding.mobileNo.text.toString().trim()
//                    val phoneNo: Long = phoneNoTxt.toLong()
//                    var phNo = phoneNo
//                    //  pr("here 2")
//                    com = ComModel(
//                        fName,
//                        lName,
//                        city,
//                        address,
//                        currentUserEmail,
//                        phNo!!,
//                        userTypeVal,
//                        age!!
//                    )
                    com.email = currentUserEmail
                    com.type = userTypeVal

                  //  pr("Com2-type : " + com.type)
                  //  pr("Com2-age : " + com.age)
                 //   pr("Com2-Phone : " + com.phoneNo)
                //    pr("Com-email : " + com.email)
                //    pr("Com-fname : " + com.fName)
                //    pr("Com-lname : " + com.lName)
                 //   pr("Com-City : " + com.city)
                //    pr("Com-addr : " + com.address)
                    binding.btnNext.setOnClickListener { goToFullProfile() }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })
    }//End-private fun setUpListenerWatchers()

    private fun goToFullProfile() {
        //   pr("goToFullProfile")
        if (1 == userTypeVal) {
            pr("Student user!")
            nextPage = npStudProf
            var intent = Intent(this, nextPage)
            intent.putExtra("stud", com)
            startActivity(intent)
            finish()
        } else if (2 == userTypeVal) {
            //    pr("Tutor user ttt!")
            nextPage = npTutorProf
            var intent = Intent(this, TutorProfile::class.java)
            intent.putExtra("tutor", com)
            startActivity(intent)
            finish()
        }
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

