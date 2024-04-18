package com.example.login_page1

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

    private var isValidQual = false;
    private var isValidExperience = false;
    private var isValidSplAchiev = false;
    private var isValidCharges = false;
    private var isValidAbtYou = false;



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
        theTutor = Tutor(
            tutor = tutorRecd,
            tutorId = "",
            qualification = "",
            experience = "",
            charges =  0.0,
            splAchieve = "",
            abtYourself = ""
        )

        binding.qualification.isEnabled = true
        binding.experience.isEnabled = false
        binding.chargesPerHour.isEnabled = false
        binding.specialAchievements.isEnabled = true
        binding.aboutYourself.isEnabled = true
        // Set up OnCheckedChangeListener for each checkbox inside onCreate
        activateCheckBoxes()
        setUpListenerWatchers()

    }//End - override fun onCreate(savedInstanceState: Bundle?)

    private fun setUpListenerWatchers() {
        binding.qualification.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle first name field changes
             //   pr("1 Qualification pressed 1")
                val qual = binding.qualification.text.toString().trim()
                isValidQual = (qual !=null && chkQual(qual))// Example validation logic
                //  validateAllInputs()
                if (isValidQual) {
                  //  pr("2 Qualification pressed 1")
                    theTutor.qualification = qual
                    binding.experience.isEnabled = true
                } else {
                  //  binding.specialAchievements.isEnabled = false
                    binding.chargesPerHour.isEnabled = false
                    binding.btnSubmitProfile.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })//End - binding.qualification.addTextChangedListener(object : TextWatcher

        binding.experience.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle last name field changes
              //  pr("1 Experience pressed 1")
                val exper = binding.experience.text.toString().trim()
                isValidExperience = (exper.isNotEmpty())// Example validation logic
                if (isValidExperience) {
                 //   pr("2 Experience pressed 1")
                    theTutor.experience = exper
                    binding.chargesPerHour.isEnabled = true
                } else {
                    binding.btnSubmitProfile.isEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })///End - binding.experience.addTextChangedListener(object : TextWatcher

        binding.chargesPerHour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
              //  pr("1 Submit Btn pressed 1")
                // Handle first name field changes
                val chgs = binding.chargesPerHour.text.toString().trim()
                isValidCharges = (chgs.isNotEmpty() && chkRate(chgs)) // Example validation logic
                if (isValidCharges) {
                    val rate = chgs.toDouble()
                    theTutor.charges = rate
                    theTutor.splAchieve = binding.specialAchievements.text.toString().trim()
                    theTutor.abtYourself =binding.aboutYourself.text.toString().trim()
                    binding.btnSubmitProfile.isEnabled = true
//                    prl("TutProfile", "fName: ", theTutor.fName)
//                    prl("TutProfile", "lName: ", theTutor.lName)
//                    prl("TutProfile", "City : ", theTutor.city)
                    binding.btnSubmitProfile.setOnClickListener { addStudUser() }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })//End - binding.chargesPerHour.addTextChangedListener(object : TextWatcher
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
              //  pr("addTutor A2 : " + tutorId)
                theTutor.setId(tutorId)
              //  pr("Tutor Id : " + theTutor.tutorId)
                tutorRef.child(tutorId).setValue(theTutor)
                    .addOnSuccessListener {
                        // User data has been saved successfully
                        pr("Tutor record saved successfully!")
                        goToHomePage()
                        // Here you can navigate to the next activity or perform any other action
                    }
                    .addOnFailureListener { e ->
                        // An error occurred while saving user data
                        pr("Error saving Tutor data: ${e.message}")
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
                         pr("Tutor GetNextId!")
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

    fun handleCheckboxChange(list: MutableList<String>, toAddVal: String, isChecked: Boolean) {
        if (isChecked) {
            // Add the toAddVal to the list if not already present
            if (!list.contains(toAddVal)) {
                list.add(toAddVal)
            }
        } else {
            // Remove the toAddVal from the list if present
            list.remove(toAddVal)
        }
    }

    fun activateCheckBoxes()
    {
        binding.checkboxCBSE.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox
            handleCheckboxChange(theTutor.boards, "CBSE", isChecked)
        }

        binding.checkboxStateBoard.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
          //  theTutor.boards.add("State")
            handleCheckboxChange(theTutor.boards, "State", isChecked)
        }

        binding.checkboxPrePrimaryTo5.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.classes.add("1to5")
            handleCheckboxChange(theTutor.classes, "1to5", isChecked)
        }

        binding.checkbox6thTo8th.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.classes.add("6to8")
            handleCheckboxChange(theTutor.classes, "6to8", isChecked)
        }

        binding.checkbox9thTo10th.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.classes.add("9to10")
            handleCheckboxChange(theTutor.classes, "9to10", isChecked)
        }

        binding.checkbox11thTo12th.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.classes.add("11to12")
            handleCheckboxChange(theTutor.classes, "11to12", isChecked)
        }

        binding.checkboxMaths.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            theTutor.subjects.add("Math")
            handleCheckboxChange(theTutor.subjects, "Math", isChecked)
        }

        binding.checkboxEnglish.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.subjects.add("English")
            handleCheckboxChange(theTutor.subjects, "English", isChecked)
        }

        binding.checkboxComputerScience.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.subjects.add("CS")
            handleCheckboxChange(theTutor.subjects, "CS", isChecked)
        }

        binding.checkboxSocialScience.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.subjects.add("SocialScience")
            handleCheckboxChange(theTutor.subjects, "SocialScience", isChecked)
        }

        binding.checkboxPhysics.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.subjects.add("Physics")
            handleCheckboxChange(theTutor.subjects, "Physics", isChecked)
        }

        binding.checkboxChemistry.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.subjects.add("Chemistry")
            handleCheckboxChange(theTutor.subjects, "Chemistry", isChecked)
        }

        binding.checkboxBiology.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.subjects.add("Biology")
            handleCheckboxChange(theTutor.subjects, "Biology", isChecked)
        }

        binding.checkboxMonday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.daysAvail.add("Monday")
            handleCheckboxChange(theTutor.daysAvail, "Monday", isChecked)
        }

        binding.checkboxTuesday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.daysAvail.add("Tuesday")
            handleCheckboxChange(theTutor.daysAvail, "Tuesday", isChecked)
        }

        binding.checkboxWednesday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.daysAvail.add("Wednesday")
            handleCheckboxChange(theTutor.daysAvail, "Wednesday", isChecked)
        }

        binding.checkboxThursday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
           // theTutor.daysAvail.add("Thursday")
            handleCheckboxChange(theTutor.daysAvail, "Thursday", isChecked)
        }

        binding.checkboxFriday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.daysAvail.add("Friday")
            handleCheckboxChange(theTutor.daysAvail, "Friday", isChecked)
        }

        binding.checkboxSaturday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.daysAvail.add("Saturday")
            handleCheckboxChange(theTutor.daysAvail, "Saturday", isChecked)
        }

        binding.checkboxSunday.setOnCheckedChangeListener { _, isChecked ->
            // Handle state change of checkbox1
            //theTutor.daysAvail.add("Sunday")
            handleCheckboxChange(theTutor.daysAvail, "Sunday", isChecked)
        }
    }

}///End - class TutorProfile : AppCompatActivity(
