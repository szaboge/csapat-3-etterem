package controllers
import globals.DatabaseConnector
import tornadofx.Controller

class LoginController: Controller() {
    fun writeToDb(value: String) {
        println(value)
        DatabaseConnector.connect()
    }
}