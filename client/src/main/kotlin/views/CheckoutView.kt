package views

import abstracts.View
import globals.UserService
import globals.order.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.icon
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.radiobutton
import globals.ui.ElementFactory.textfield
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.validate
import globals.ui.ElementFactory.validateByClass
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import models.communication.FoodsCountModel
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLMenuElement
import org.w3c.dom.HTMLMenuItemElement
import kotlin.dom.addClass
import kotlin.dom.removeClass

class CheckoutView : View() {
    override val routeType: Routes = Routes.CHECKOUT
    lateinit var nevField: HTMLInputElement
    lateinit var emailField: HTMLInputElement
    lateinit var telefonszamField: HTMLInputElement
    lateinit var irszamField: HTMLInputElement
    lateinit var telepulesField: HTMLInputElement
    lateinit var utcaField: HTMLInputElement
    lateinit var hazszamField: HTMLInputElement
    lateinit var cash: HTMLInputElement
    lateinit var card: HTMLInputElement
    lateinit var pay: String


    override fun render() {
        root.div {
            addClass("checkout")
            div {
                addClass("checkout-label-title")
                label(Lang.getText("checkout-checkout-your-order")) {
                    addClass("title")
                }
            }
            div {
                addClass("ck")
                nevField = textfield {
                    addClass("default-textfield")
                    placeholder = "Név"
                    addEventListener("keyup", {
                        validateByClass("name", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                emailField = textfield {
                    addClass("default-textfield")
                    placeholder = "Email"
                    addEventListener("keyup", {
                        validateByClass("email", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                telefonszamField = textfield {
                    addClass("default-textfield")
                    placeholder = "Telefonszám"
                    addEventListener("keyup", {
                        validateByClass("phone", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                irszamField = textfield {
                    addClass("default-textfield")
                    placeholder = "Irányítószám"
                    addEventListener("keyup", {
                        validateByClass("zipcode", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                telepulesField = textfield {
                    addClass("default-textfield")
                    placeholder = "Település"
                    addEventListener("keyup", {
                        validateByClass("city", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                utcaField = textfield {
                    addClass("default-textfield")
                    placeholder = "Utca"
                    addEventListener("keyup", {
                        validateByClass("street", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                hazszamField = textfield {
                    addClass("default-textfield")
                    placeholder = "Házszám"
                    addEventListener("keyup", {
                        validateByClass("street_number", "valid", "invalid")
                    })
                }
            }
            div {
                addClass("ck")
                label(Lang.getText("checkout-payment" ))
                div {
                    addClass("ck")
                    icon("credit-card") {
                        addClass("checkout-image")
                    }
                    label(Lang.getText("checkout-credit-card" ))
                    cash = radiobutton("payment", true)
                }
                div {
                    addClass("ck")
                    icon("cash-multiple") {
                        addClass("checkout-image")
                    }
                    label(Lang.getText("checkout-cash" ))
                    card = radiobutton("payment", false)
                }
            }

            pay = if (cash.checked) "cash" else "credit-card"

            div {
                addClass("ck")
                button(Lang.getText("checkout-order" )) {
                    addClass("default-button")
                    addEventListener("click", {
                        continue_checkout()
                    })
                }
            }
        }//div: checkout
    }

    fun continue_checkout() {
        val name: String = nevField.value
        val email: String = emailField.value
        val phone: String = telefonszamField.value
        val zipcode: String = irszamField.value
        val city: String = telepulesField.value
        val street: String = utcaField.value
        val strnumber: String = hazszamField.value
        val payment: String = pay

        if (nevField.validateByClass("name", "valid", "invalid")
            && emailField.validateByClass("email", "valid", "invalid")
            && telefonszamField.validateByClass("phone", "valid", "invalid")
            && irszamField.validateByClass("zipcode", "valid", "invalid")
            && telepulesField.validateByClass("city", "valid", "invalid")
            && utcaField.validateByClass("street", "valid", "invalid")
            && hazszamField.validateByClass("street_number", "valid", "invalid")
        ) {
            OrderService.makeOrder(name, email, phone, zipcode, city, street, strnumber, payment)
        }
    }

    override fun onShow() {
    }
}