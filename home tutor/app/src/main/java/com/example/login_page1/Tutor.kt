
import com.example.login_page1.ComModel
import java.io.Serializable
import java.math.BigDecimal

class Tutor(
    tutor: ComModel,
    var tutorId: String = "",
    var qualification: String = "",
    var experience: String = "",
    var boards: MutableList<String> = mutableListOf(),
    var classes: MutableList<String> = mutableListOf(),
    var subjects: MutableList<String> = mutableListOf(),
    var daysAvail: MutableList<String> = mutableListOf(),
    var charges: BigDecimal = BigDecimal.ZERO,
    var splAchieve: String = "",
    var abtYourself: String = ""
) : ComModel(
    tutor.fName, tutor.lName, tutor.city, tutor.address,
    tutor.email, tutor.phoneNo, tutor.type, tutor.age
), Serializable {

    // Setter method for tutorId
    fun setId(id: String) {
        this.tutorId = id
    }

    fun addBoard(aBd : String)
    {
        this.boards.add(aBd)
    }

    fun addClasses(aClass : String)
    {
        this.classes.add(aClass)
    }
    fun addSubjects(aSubj : String)
    {
        this.subjects.add(aSubj)
    }
    fun addDaysAvail(aDay : String)
    {
        this.daysAvail.add(aDay)
    }
    // Consider adding additional methods for manipulating the properties if needed.
}
