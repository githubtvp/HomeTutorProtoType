package com.example.login_page1;

import android.os.Bundle;
//import androidx.activity.enableEdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class FbCrud extends AppCompatActivity {

    RecyclerView rv;
    UserAdapter ua;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_crud);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserModel> options =
                new FirebaseRecyclerOptions.Builder<UserModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("users"), UserModel.class).build();
    ua = new UserAdapter(options);
    rv.setAdapter(ua);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ua.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ua.stopListening();
    }

}