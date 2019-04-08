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
                    //Név
                    addClass("checkout-textfield ")
                    placeholder = "Név"
                    addEventListener("keyup", {
                        if (!validate("name")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                emailField = textfield {
                    //Email
                    addClass("checkout-textfield ")
                    placeholder = "Email"
                    addEventListener("keyup", {
                        if (!validate("email")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                telefonszamField = textfield {
                    //Telefonszám
                    addClass("checkout-textfield ")
                    placeholder = "Telefonszám"
                    addEventListener("keyup", {
                        if (!validate("phone")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                irszamField = textfield {
                    //Irányítószám
                    addClass("checkout-textfield ")
                    placeholder = "Irányítószám"
                    addEventListener("keyup", {
                        if (!validate("zipcode")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                telepulesField = textfield {
                    //Település
                    addClass("checkout-textfield ")
                    placeholder = "Település"
                    addEventListener("keyup", {
                        if (!validate("city")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                utcaField = textfield {
                    //Utca
                    addClass("checkout-textfield ")
                    placeholder = "Utca"
                    addEventListener("keyup", {
                        if (!validate("street")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
                    })
                }
            }
            div {
                addClass("ck")
                hazszamField = textfield {
                    //Házszám
                    addClass("checkout-textfield ")
                    placeholder = "Házszám"
                    addEventListener("keyup", {
                        if (!validate("street_number")) {
                            removeClass("good")
                            addClass("bad")
                        } else {
                            removeClass("bad")
                            addClass("good")
                        }
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

        val data = object {
            val name: String = nevField.value
            val email: String = emailField.value
            val phone: String = telefonszamField.value
            val zipcode: String = irszamField.value
            val city: String = telepulesField.value
            val street: String = utcaField.value
            val strnumber: String = hazszamField.value
            val payment: String = pay
        }
        if (nevField.validate("name") && emailField.validate("email") && telefonszamField.validate("phone") && irszamField.validate(
                "zipcode"
            ) && telepulesField.validate("city")
            && utcaField.validate("street") && hazszamField.validate("street_number")
        ) {
            OrderService.makeOrder(name, email, phone, zipcode, city, street, strnumber, payment)
        } else {
            if (!nevField.validate("name")) {
                nevField.removeClass("good")
                nevField.addClass("bad")
            }
            if (!emailField.validate("email")) {
                emailField.removeClass("good")
                emailField.addClass("bad")
            }
            if (!telefonszamField.validate("phone")) {
                telefonszamField.removeClass("good")
                telefonszamField.addClass("bad")
            }
            if (!irszamField.validate("phone")) {
                irszamField.removeClass("good")
                irszamField.addClass("bad")
            }
            if (!telepulesField.validate("phone")) {
                telepulesField.removeClass("good")
                telepulesField.addClass("bad")
            }
            if (!utcaField.validate("phone")) {
                utcaField.removeClass("good")
                utcaField.addClass("bad")
            }
            if (!hazszamField.validate("phone")) {
                hazszamField.removeClass("good")
                hazszamField.addClass("bad")
            }
        }
    }

    override fun onShow() {
    }
}