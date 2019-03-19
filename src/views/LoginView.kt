package views
import controllers.LoginController
import globals.Constants
import javafx.scene.paint.Color
import tornadofx.*

class LoginView : View() {
    private val controller: LoginController by inject()
    override val root = vbox{
        style {
            backgroundColor += Color.ALICEBLUE
        }
        flowpane {
            useMaxWidth = true
            label("Hello LoginView")
        }
    }
}