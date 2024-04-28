package com.example.login_page1

import Tutor
import TutorAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page1.databinding.ActivityShowTutorsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ShowTutors : AppCompatActivity() {
    private lateinit var binding: ActivityShowTutorsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var tutorAdapter: TutorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTutorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerViewTutors
        recyclerView.layoutManager = LinearLayoutManager(this)

        //  tutorAdapter = TutorAdapter(emptyList()) // Initially, no data
        tutorAdapter = TutorAdapter(mutableListOf()) // Initially, no data

        recyclerView.adapter = tutorAdapter

        binding.btnGetRecords.setOnClickListener { fetchAllRecords(tutorAdapter) }
    }
    //binding.btnNext.setOnClickListener { fetchAllRecords() }

    // Fetch all records from Firebase
    private fun fetchAllRecords(tutorAdapter: TutorAdapter) {
        // Step 1: Get a reference to your Firebase Realtime Database
        val db = FirebaseDatabase.getInstance()
        // Step 2: Define a reference to the location where you want to store the user data
        val tutorRef = db.getReference("Tutor")

        val allTutors = mutableListOf<Tutor>()
        tutorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allTutors.clear()
                for (data in snapshot.children) {
                    // Here, you should be getting Tutor objects instead of Result
                    val tutor = data.getValue(Tutor::class.java)
                    tutor?.let { allTutors.add(it) }
                    pr("Here ")
                    prl("Tut", "FName", tutor?.fName ?: "defaultName")
                }
                // Now you can use allTutors list containing all fetched tutors
                // For example, you can update your UI here
                //    tutorAdapter.tutors = allTutors // Update adapter with new data
                tutorAdapter.submitMutableList(allTutors) // Update adapter with new data
                // tutorAdapter.notifyDataSetChanged() // Notify adapter about the data change

            }
            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

}

