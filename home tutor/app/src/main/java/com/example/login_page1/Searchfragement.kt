package com.example.login_page1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query

// Import necessary Firebase and RecyclerView dependencies

class Searchfragement : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    //private lateinit var adapter: SearchUserRecyclerAdapter // Declare adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchInput = view.findViewById(R.id.seach_username_input)
        searchButton = view.findViewById(R.id.search_user_btn)
        backButton = view.findViewById(R.id.back_btn)
        recyclerView = view.findViewById(R.id.search_user_recycler_view)

        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        searchButton.setOnClickListener {
            val searchTerm = searchInput.text.toString()
            if (searchTerm.isEmpty() || searchTerm.length < 3) {
                searchInput.error = "Invalid Username"
                return@setOnClickListener
            }
            setupSearchRecyclerView(searchTerm)
        }

        return view
    }

    private fun setupSearchRecyclerView(searchTerm: String) {
        // Setup RecyclerView and adapter here
    }

    override fun onStart() {
        super.onStart()
        // Start listening to adapter here
    }

    override fun onStop() {
        super.onStop()
        // Stop listening to adapter here
    }

    override fun onResume() {
        super.onResume()
        // Start listening to adapter here
    }
}