
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page1.R

class TutorAdapter(var tutors: MutableList<Tutor>) : RecyclerView.Adapter<TutorAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewFirstName: TextView = itemView.findViewById(R.id.textViewFirstName)
        val textViewLastName: TextView = itemView.findViewById(R.id.textViewLastName)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val textViewClasses: TextView = itemView.findViewById(R.id.textViewClasses)
        val textViewCharges: TextView = itemView.findViewById(R.id.textViewCharges)
        val textViewExperience: TextView = itemView.findViewById(R.id.textViewExperience)
        val textViewSubjects: TextView = itemView.findViewById(R.id.textViewSubjects)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tutor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tutor = tutors[position]
        holder.textViewFirstName.text = tutor.fName
        holder.textViewLastName.text = tutor.lName
        holder.textViewLocation.text = "Location: ${tutor.city}, ${tutor.address}"
        holder.textViewPhoneNumber.text = "Phone Number: ${tutor.phoneNo}"
        holder.textViewClasses.text = "Classes: ${tutor.classes.joinToString(", ")}"
        holder.textViewCharges.text = "Charges: ${tutor.charges}"
        holder.textViewExperience.text = "Experience: ${tutor.experience}"
        holder.textViewSubjects.text = "Subjects: ${tutor.subjects.joinToString(", ")}"
    }

    override fun getItemCount(): Int {
        return tutors.size
    }

    fun submitMutableList(newTutors: MutableList<Tutor>) {
        tutors.clear() // Clear the existing list of tutors
        tutors.addAll(newTutors) // Add all the new tutors to the list
        notifyDataSetChanged() // Notify RecyclerView of the data change
    }

}
