package screens
import controllers.LoginController
import tornadofx.*

class Login : View() {
    private val controller: LoginController by inject()
    override val root = vbox {
        setPrefSize(800.0,600.0)
        button("Insert") {
            action {
                controller.insert()
            }
        }
        label("Ez egy label")
    }
}