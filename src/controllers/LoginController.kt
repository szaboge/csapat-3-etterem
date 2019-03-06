package controllers
import tornadofx.Controller

class LoginController: Controller() {
    fun writeToDb(value: String) {
        println(value)
    }
}