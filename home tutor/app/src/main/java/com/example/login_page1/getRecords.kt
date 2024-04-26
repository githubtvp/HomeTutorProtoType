package com.example.login_page1

import Tutor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_page1.databinding.ActivityCommonProfileBinding
import com.example.login_page1.databinding.ActivityGetRecordsBinding
import com.example.login_page1.databinding.ActivityTutorProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class getRecords : AppCompatActivity() {
    private lateinit var binding: ActivityGetRecordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnGetRecords.setOnClickListener { fetchAllRecords() }
    }
    //binding.btnNext.setOnClickListener { fetchAllRecords() }

}

// Fetch all records from Firebase
private fun fetchAllRecords() {
    // Step 1: Get a reference to your Firebase Realtime Database
    val db = FirebaseDatabase.getInstance()
    // Step 2: Define a reference to the location where you want to store the user data
    val tutorRef = db.getReference("Tutor")

    val allTutors = mutableListOf<TutorModel>()
    tutorRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            allTutors.clear()
            for (data in snapshot.children) {
                // Here, you should be getting TutorModel objects instead of Result
                val tutor = data.getValue(TutorModel::class.java)
                tutor?.let { allTutors.add(it) }
               // prl("Tut", "FName", tutor?.fName);
                prl("Tut", "FNameTVP", tutor?.fName ?: "defaultName")
            }
            // Now you can use allTutors list containing all fetched tutors
            // For example, you can update your UI here
        }
        override fun onCancelled(error: DatabaseError) {
            // Handle errors
        }
    })
}




// Update RecyclerView with search results
private fun updateSearchResults(results: List<TutorModel>) {
    // Update RecyclerView adapter
    //adapter.submitList(results)
}

/*
// Initialize Firebase
FirebaseApp.initializeApp(this)
val database = FirebaseDatabase.getInstance()
val databaseReference = database.reference.child("your_database_node")

// Fetch all records from Firebase
private fun fetchAllRecords() {
    val allRecords = mutableListOf<Result>()
    databaseReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            allRecords.clear()
            for (data in snapshot.children) {
                val result = data.getValue(Result::class.java)
                result?.let { allRecords.add(it) }
            }
            // You can store allRecords locally and update UI as needed
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle errors
        }
    })
}

// Listen for changes in the search input
searchEditText.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Perform local search when text changes
        performLocalSearch(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {}
})

// Perform local search
private fun performLocalSearch(query: String) {
    val filteredResults = allRecords.filter { result ->
        result.name.contains(query, ignoreCase = true) // Adjust this condition according to your search criteria
    }
    updateSearchResults(filteredResults)
}

// Update RecyclerView with search results
private fun updateSearchResults(results: List<Result>) {
    // Update RecyclerView adapter
    adapter.submitList(results)
}

// Initialize Firebase
FirebaseApp.initializeApp(this)
val db = FirebaseDatabase.getInstance()
val dbTbl = db.reference.child("tutor")

// Listen for changes in the search input
searchEditText.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Perform search when text changes
        performSearch(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {}
})

// Perform search
private fun performSearch(query: String) {
    val searchQuery = databaseReference.orderByChild("name").startAt(query).endAt(query + "\uf8ff")
    searchQuery.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // Handle search results
            val results = mutableListOf<Result>()
            for (data in snapshot.children) {
                val result = data.getValue(Result::class.java)
                result?.let { results.add(it) }
            }
            // Update RecyclerView with search results
            updateSearchResults(results)
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle errors
        }
    })
}

// Update RecyclerView with search results
private fun updateSearchResults(results: List<Result>) {
    // Update RecyclerView adapter
    adapter.submitList(results)
}


// Define RecyclerView in XML layout file
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

// Create Adapter and ViewHolder
class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // Implement methods for RecyclerView.Adapter
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Define views within each item layout
}

// Set up RecyclerView in Activity or Fragment
val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = MyAdapter()

 */