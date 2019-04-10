package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.ElementFactory.textfield
import globals.ui.ElementFactory.validateByClass
import globals.ui.ElementFactory.validate
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import kotlin.dom.addClass

class RegistrationView: View() {
    override val routeType: Routes = Routes.REGISTRATION
    lateinit var nameField: HTMLInputElement
    lateinit var emailField: HTMLInputElement
    lateinit var passwordField: HTMLInputElement
    lateinit var repasswordField: HTMLInputElement
    lateinit var submitButton: HTMLButtonElement
    lateinit var error: HTMLSpanElement

    override fun render() {
        with(root) {
            addClass("registration-container")
            div {
                addClass("registration-wrapper")
                div {
                    addClass("title-wrapper")
                    label(Lang.getText("registration").toUpperCase()) {
                        addClass("title")
                    }
                }
                div {
                    addClass("registration-fields")
                    nameField = textfield {
                        addClass("default-textfield")
                        placeholder = Lang.getText("name")
                        addEventListener("keyup", {
                            validateByClass("name", "valid", "invalid")
                            checkAll()
                        })
                        maxLength = 40
                    }
                    emailField = textfield {
                        addClass("default-textfield")
                        placeholder = Lang.getText("email")
                        addEventListener("keyup", {
                            validateByClass("email", "valid", "invalid")
                            checkAll()
                        })
                        maxLength = 40
                    }
                    passwordField = textfield {
                        addClass("default-textfield")
                        placeholder = Lang.getText("password")
                        addEventListener("keyup", {
                            validateByClass("password", "valid", "invalid")
                            checkAll()
                        })
                        maxLength = 40
                    }
                    repasswordField = textfield {
                        addClass("default-textfield with-hint")
                        placeholder = Lang.getText("repassword")
                        addEventListener("keyup", {
                            validateByClass("password", "valid", "invalid")
                            checkAll()
                        })
                        maxLength = 40
                    }
                    span(Lang.getText("registration-hint")) {
                        addClass("hint")
                    }
                }
                submitButton = button(Lang.getText("registration")) {
                    addClass("default-button")
                    addEventListener("click", {
                        submit()
                    })
                }
                error = span {
                    addClass("hint-error")
                }
            }
        }
        checkAll()
    }

    private fun checkAll() {
        val valid = !(emailField.validate("email") && nameField.validate("name")
                && passwordField.validate("password") && repasswordField.validate("password")
                && passwordField.value == repasswordField.value)
        submitButton.disabled = valid
    }

    fun submit() {
        val data = object {
            val name = nameField.value
            val email = emailField.value
            val password = passwordField.value
        }
        submitButton.disabled = true
        ApiService.register(data) {
            val status = it.status.toInt()
            if (status == 200) {
                RouterService.navigate(Routes.REGISTRATIONDONE)
            } else {
                error.textContent = Lang.getText("registration-error")
                checkAll()
            }
        }
    }

    override fun onShow() {

    }
}