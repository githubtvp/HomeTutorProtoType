import com.example.login_page1.ComModel
import com.example.login_page1.StudModel
import java.io.Serializable

class Tutor(
    stud: ComModel,
    var studId: String = "",
    var qualification: String = "",
    var experience: String = "",
    var stateBd: Boolean = true,
    var centralBd: Boolean = false,
    var classes: Long = 0
) : ComModel(
    stud.fName, stud.lName, stud.city, stud.address,
    stud.email, stud.phoneNo, stud.type, stud.age
), Serializable {
    // Setter method for userType
    fun setId (id: String) {
        this.studId = id
    }
}
