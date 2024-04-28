package com.example.login_page1.adapter

//import com.example.login_page1.Chatfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page1.ComModel
import com.example.login_page1.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class SearchUserRecyclerAdapter(options: FirestoreRecyclerOptions<ComModel>, private val context: Context) :
    FirestoreRecyclerAdapter<ComModel, SearchUserRecyclerAdapter.UserModelViewHolder>(options) {

    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int, model: ComModel) {
        holder.usernameText.text = model.fName
        holder.phoneText.text = model.phoneNo.toString()
//        if (model.userId == FirebaseUtil.currentUserId()) {
//            holder.usernameText.text = model.fName + " (Me)"
//        }

//        FirebaseUtil.getOtherProfilePicStorageRef(model.userId).downloadUrl.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val uri: Uri? = task.result
//                uri?.let { AndroidUtil.setProfilePic(context, it, holder.profilePic) }
//            }
//        }

//        holder.itemView.setOnClickListener {
//            // navigate to chat activity
//            val intent = Intent(context, Chatfragment::class.java).apply {
//                AndroidUtil.passUserModelAsIntent(this, model)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }
//            context.startActivity(intent)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_view, parent, false)
        return UserModelViewHolder(view)
    }

    inner class UserModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.user_name_text)
        val phoneText: TextView = itemView.findViewById(R.id.phone_text)
        val profilePic: ImageView = itemView.findViewById(R.id.profile_pic_image_view)
    }
}

/*

class TutorAdapter(private val tutors: List<TutorModel>) :
    RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

    inner class TutorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tutorNameTextView: TextView = itemView.findViewById(R.id.tutorNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tutor, parent, false)
        return TutorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        val tutor = tutors[position]
        holder.tutorNameTextView.text = tutor.fName ?: "defaultName"
    }

    override fun getItemCount(): Int {
        return tutors.size
    }
}

 */