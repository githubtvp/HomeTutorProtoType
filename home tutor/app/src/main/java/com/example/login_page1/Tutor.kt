
import com.example.login_page1.ComModel
import java.io.Serializable

class Tutor(
    tutor: ComModel,
    var tutorId: String = "",
    var qualification: String = "",
    var experience: String = "",
    var stateBd: Boolean = true,
    var centralBd: Boolean = false,
    var classes: Long = 0
) : ComModel(
    tutor.fName, tutor.lName, tutor.city, tutor.address,
    tutor.email, tutor.phoneNo, tutor.type, tutor.age
), Serializable {
    // Setter method for userType
    fun setId (id: String) {
        this.tutorId = id
    }
}
