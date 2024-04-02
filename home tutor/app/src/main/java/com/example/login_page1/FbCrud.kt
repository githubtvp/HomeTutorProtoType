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
/*
val database = FirebaseDatabase.getInstance()
val usersRef = database.getReference("users")


// Read data from the "users" collection
usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (snapshot in dataSnapshot.children) {
            val user = snapshot.getValue(User::class.java)
            // Handle the retrieved user data
        }
    }

    override fun onCancelled(databaseError: DatabaseError) {
        // Handle error
    }
})

// Write data to the "users" collection
val newUserRef = usersRef.push()
val newUser = User("New User", 20)
newUserRef.setValue(newUser)


 */