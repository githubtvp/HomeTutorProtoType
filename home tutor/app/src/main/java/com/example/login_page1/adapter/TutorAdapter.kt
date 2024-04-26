import androidx.recyclerview.widget.RecyclerView
import com.example.login_page1.TutorModel

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
