package com.example.login_page1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class FbCrud : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var ua: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fb_crud)
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        val options = FirebaseRecyclerOptions.Builder<UserModel>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("users"),
                UserModel::class.java
            )
            .build()
        ua = UserAdapter(options)
        rv.adapter = ua
    }

    override fun onStart() {
        super.onStart()
        ua.startListening()
    }

    override fun onStop() {
        super.onStop()
        ua.stopListening()
    }
}
