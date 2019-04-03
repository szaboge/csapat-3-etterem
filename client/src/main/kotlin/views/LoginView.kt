package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.textfield
import globals.ui.Routes
import org.w3c.dom.HTMLInputElement
import kotlin.dom.addClass

class LoginView : View() {
    override val routeType: Routes = Routes.LOGIN
    lateinit var emailField: HTMLInputElement
    lateinit var passwordField: HTMLInputElement

    override fun render(){
        root.addClass("login-container")
        root.div {
            addClass("login-box")
            emailField = textfield("email") {
                addClass("default-textfield")
            }
            passwordField = textfield("password") {
                addClass("default-textfield")
            }
            button("LOGIN") {
                addClass("default-button")
                addEventListener("click", {
                    login()
                })
            }
        }
    }

    fun login() {
        val data = object {
            val email: String = emailField.value
            val password: String = passwordField.value
        }
        ApiService.login(data) {
            println(it.status)
        }
    }

    override fun onShow() {
    }
}