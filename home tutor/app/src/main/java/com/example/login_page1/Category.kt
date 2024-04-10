package com.example.login_page1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        buttonNext.setOnClickListener {
            // Retrieve checked subjects
            val checkedSubjects = mutableListOf<String>()

            val checkBoxMath = findViewById<CheckBox>(R.id.checkBoxMath)
            val checkBoxPhysics = findViewById<CheckBox>(R.id.checkBoxPhysics)
            val checkBoxChemistry = findViewById<CheckBox>(R.id.checkBoxChemistry)
            val checkBoxBiology = findViewById<CheckBox>(R.id.checkBoxBiology)
            val checkBoxIT = findViewById<CheckBox>(R.id.checkBoxIT)

            if (checkBoxMath.isChecked) {
                checkedSubjects.add("Math")
            }
            if (checkBoxPhysics.isChecked) {
                checkedSubjects.add("Physics")
            }
            if (checkBoxChemistry.isChecked) {
                checkedSubjects.add("Chemistry")
            }
            if (checkBoxBiology.isChecked) {
                checkedSubjects.add("Biology")
            }
            if (checkBoxIT.isChecked) {
                checkedSubjects.add("IT")
            }

            // Retrieve checked classes
            val checkedClasses = mutableListOf<String>()

            val checkBox5 = findViewById<CheckBox>(R.id.checkBox5)
            val checkBox6 = findViewById<CheckBox>(R.id.checkBox6)
            val checkBox7 = findViewById<CheckBox>(R.id.checkBox7)
            val checkBox8 = findViewById<CheckBox>(R.id.checkBox8)
            val checkBox9 = findViewById<CheckBox>(R.id.checkBox9)

            if (checkBox5.isChecked) {
                checkedClasses.add("Class 5")
            }
            if (checkBox6.isChecked) {
                checkedClasses.add("Class 6")
            }
            if (checkBox7.isChecked) {
                checkedClasses.add("Class 7")
            }
            if (checkBox8.isChecked) {
                checkedClasses.add("Class 8")
            }
            if (checkBox9.isChecked) {
                checkedClasses.add("Class 9")
            }

            // Display checked subjects and classes
            //  val message = "Checked subjects: $checkedSubjects\nChecked classes: $checkedClasses"
            // Toast.makeText(this, message, Toast.LENGTH_LONG).show()
           // readUser("u1")
            getUserData()
        }

    }

    fun readUser(userId: String) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        // Create a listener to fetch the user data
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the dataSnapshot exists and contains data
                if (dataSnapshot.exists()) {
                    // Retrieve user data as a Map
                    val userData = dataSnapshot.value as? Map<String, Any?>

                    // Do something with the user data
                    if (userData != null) {
                        // Iterate through the map entries to access all attributes
                        for ((key, value) in userData) {
                            pr("$key: $value")
                        }
                    } else {
                        pr("User data is null")
                    }
                } else {
                    pr("User not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                pr("Error fetching user data: ${databaseError.message}")
            }
        })
    }

    private fun getUserData() {
        // Initialize Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()

        // Get a reference to the "users" node in the database
        val usersRef = database.getReference("users")

        // Create a list to hold your data
        val myDataList = mutableListOf<UserModel1>()

        // Query the "students" collection and retrieve the last record
        val userQuery = usersRef.orderByChild("id").limitToLast(1)


// Read data from the "users" node
        userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the last student record
                    val lastStudentRecord = dataSnapshot.children.last().getValue(UserModel1::class.java)
                    pr("Id : " + lastStudentRecord?.id)
                } else {
                    pr("No student data available")
                }



//                for (snapshot in dataSnapshot.children) {
//                    // Convert each snapshot to your data class
//                    val user = snapshot.getValue(UserModel1::class.java)
//                    // Add the user to the list
//                    user?.let {
//                        myDataList.add(it)
//                    }
//                }
//                // Now myDataList contains instances of UserAttribute retrieved from Realtime Database
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        // Print each item retrieved
        myDataList.forEach { user ->
            pr("First Name: ${user.fName}")
            pr("Last Name: ${user.lName}")
            pr("City: ${user.city}")
            pr("Address: ${user.address}")
            pr("Age: ${user.age}")
            pr("Id: ${user.id}")
        }
    }
}


