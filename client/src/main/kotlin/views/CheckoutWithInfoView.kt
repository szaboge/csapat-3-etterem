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
import globals.ui.ElementFactory.span
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.input
import globals.ui.ElementFactory.validate
import globals.ui.ElementFactory.validateByClass
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import models.communication.FoodsCountModel
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass

class CheckoutWithInfoView : View() {
    override val routeType: Routes = Routes.CHECKOUTWITHINFO
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

    var phoneToggle = false
    var nameToggle = false
    var emailToggle = false
    var addressToggle = false

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
                addClass("checkout-section")
                label("Személyes adatok")
                div {
                    div {
                        nevField = getTextField(this, "Név", "name", "Minta János")
                    }
                    div {
                        emailField = getTextField(this, "Email", "email", "minta@minta.hu")
                    }

                    div("checkout-item-container") {
                        div {
                            telefonszamField = getTextField(this, "Telefonszám", "phone", "06709999999")
                        }
                        div {
                            createCheckbox(this, "phone", "Új")
                        }
                    }
                }
            }
            div {
                addClass("checkout-section")
                label("Szállítási cím")
                div {
                    addClass("checkout-section-items")
                    irszamField = textfield {
                        addClass("default-textfield")
                        placeholder = "Irányítószám"
                        addEventListener("keyup", {
                            validateByClass("zipcode", "valid", "invalid")
                        })
                    }
                    telepulesField = textfield {
                        addClass("default-textfield")
                        placeholder = "Település"
                        addEventListener("keyup", {
                            validateByClass("city", "valid", "invalid")
                        })
                    }
                }
            }
            div {
                addClass("checkout-section")
                label(Lang.getText("checkout-payment"))
                div {
                    addClass("checkout-section-items")
                    div {
                        addClass("checkout-radiobutton")
                        icon("credit-card") {
                            addClass("checkout-image")
                        }
                        label(Lang.getText("checkout-credit-card")) {
                            setAttribute("for", "cash")
                        }
                        cash = radiobutton("payment", true) {
                            id = "cash"
                        }
                    }
                    div {
                        addClass("checkout-radiobutton")
                        icon("cash-multiple") {
                            addClass("checkout-image")
                        }
                        label(Lang.getText("checkout-cash")) {
                            setAttribute("for", "card")
                        }
                        card = radiobutton("payment", false) {
                            id = "card"
                        }
                    }
                }
            }

            div {
                button(Lang.getText("checkout-order")) {
                    addClass("default-button")
                    addEventListener("click", {
                        continue_checkout()
                    })
                }
            }
        }
    }

    fun createCheckbox(root: HTMLElement, name: String, text: String) {
        with(root) {
            input("checkbox") {
                id = name
                addEventListener("change", {

                })
            }
            label(text) {
                setAttribute("for", name)
            }
        }
    }

    fun getTextField(root: HTMLElement, name: String, validationType: String, tooltipText: String): HTMLInputElement {
        lateinit var item: HTMLInputElement
        root.div {
            addClass("tooltip")
            span(tooltipText) {
                addClass("tooltiptext")
            }
            item = textfield {
                addClass("default-textfield")
                placeholder = name
                addEventListener("keyup", {
                    validateByClass(validationType, "valid", "invalid")
                })
            }
        }
        return item
    }

    fun continue_checkout() {
        val name: String = nevField.value
        val email: String = emailField.value
        val phone: String = telefonszamField.value
        val zipcode: String = irszamField.value
        val city: String = telepulesField.value
        val street: String = utcaField.value
        val strnumber: String = hazszamField.value
        val payment: String = if (cash.checked) "cash" else "credit-card"

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