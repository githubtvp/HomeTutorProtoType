package com.example.login_page1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

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
            val message = "Checked subjects: $checkedSubjects\nChecked classes: $checkedClasses"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}

