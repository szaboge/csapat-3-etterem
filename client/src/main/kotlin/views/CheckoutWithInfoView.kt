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
import globals.ui.ElementFactory.input
import globals.ui.ElementFactory.table
import globals.ui.ElementFactory.td
import globals.ui.ElementFactory.tr
import globals.ui.ElementFactory.validateByClass
import globals.ui.Lang
import globals.ui.Routes
import globals.validation.Validators
import models.communication.AddressesModel
import org.w3c.dom.*
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

    var items: MutableMap<String, Pair<HTMLDivElement, HTMLDivElement>> = mutableMapOf()
    var chips: MutableMap<String, MutableList<HTMLElement>> = mutableMapOf()
    var checks: MutableMap<String, Boolean> = mutableMapOf()
    var choosed: MutableMap<String, Int> = mutableMapOf()

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
                table {
                    tr {
                        addClass("checkout-tr")
                        td("checkout-td1") {

                        }
                        td("checkout-td-main") {
                            val item1 = div("checkout-hide") {
                                nevField = getTextField(this, "Név", "name", "Minta János")
                            }
                            val item2 = div("checkout-chips-container") {
                                UserService.userInfo?.names?.forEachIndexed { index, element ->
                                    val chip = createChips(element, index, "name")
                                    addChips("name", chip)
                                    appendChild(chip)
                                }
                            }
                            items["name"] = Pair(item1, item2)
                        }

                        td("checkout-td1") {
                            createCheckbox(this, "name", "Új")
                        }
                    }
                    tr {
                        td("checkout-td1") {

                        }
                        td("checkout-td-main") {
                            val item1 = div("checkout-hide") {
                                emailField = getTextField(this, "Email", "email", "minta@minta.hu")
                            }
                            val item2 = div("checkout-chips-container") {
                                UserService.userInfo?.emails?.forEachIndexed {index, element ->
                                    val chip = createChips(element,index, "email")
                                    addChips("email", chip)
                                    appendChild(chip)
                                }
                            }
                            items["email"] = Pair(item1, item2)
                        }
                        td("checkout-td1") {
                            createCheckbox(this, "email", "Új")
                        }
                    }
                    tr {
                        td("checkout-td1") {

                        }
                        td("checkout-td-main") {
                            val item1 = div("checkout-hide") {
                                telefonszamField = getTextField(this, "Telefonszám", "phone", "06709999999")
                            }
                            val item2 =  div("checkout-chips-container") {

                                UserService.userInfo?.phones?.forEachIndexed { index, element ->
                                    val chip = createChips(element, index,"phone")
                                    addChips("phone", chip)
                                    appendChild(chip)
                                }
                            }
                            items["phone"] = Pair(item1, item2)
                        }
                        td("checkout-td1") {
                            createCheckbox(this, "phone", "Új")
                        }
                    }
                }
            }
            div {
                addClass("checkout-section")
                label("Szállítási cím")
                table {
                    tr {
                        addClass("checkout-tr")
                        td("checkout-td1") {

                        }
                        td("checkout-td-main") {
                            val item1 = div("checkout-hide checkout-normal-container") {
                                irszamField = getTextField(this, "Irányítószám", "zipcode", "4 darab számjegy")
                                telepulesField = getTextField(this, "Település", "city", "Magyar abc betűi")
                                utcaField = getTextField(this, "Utca", "street", "Magyar abc betűi")
                                hazszamField = getTextField(this, "Házszám", "street_number", "Szám formátum")
                            }
                            val item2 = div("checkout-chips-container") {
                                UserService.userInfo?.addresses?.forEachIndexed { index, element ->
                                    val chip = createAddress(element, index, "address")
                                    addChips("address", chip)
                                    appendChild(chip)
                                }
                            }
                            items["address"] = Pair(item1, item2)
                        }

                        td("checkout-td1") {
                            createCheckbox(this, "address", "Új")
                        }
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

    private fun createAddress(item: AddressesModel,index: Int, type: String): HTMLElement {
        return with(div()) {
            addClass("default-chips checkout-address")
            label(item.zipcode.toString())
            label(item.city)
            label(item.street + " " + item.strnumber)
            addEventListener("click", {
                chips[type]!!.forEach {
                    it.removeClass("chips-highlight")
                }
                addClass("chips-highlight")
                choosed[type] = index
            })
            this
        }
    }

    private fun addChips(text: String, chip: HTMLElement) {
        if (!chips.containsKey(text)) chips[text] = mutableListOf()
        chips[text]!!.add(chip)
    }

    fun createCheckbox(root: HTMLElement, name: String, text: String) {
        with(root) {
            addClass("checkbox-container")
            input("checkbox") {
                id = name
                addEventListener("change", {
                    checks[name] = (it.target as HTMLInputElement).checked
                    toggleDivs(name)
                })
                checks[name] = false
            }
            label(text) {
                setAttribute("for", name)
            }
        }
    }

    fun createChips(text: String, index: Int, type: String): HTMLButtonElement {
        return with(button()) {
            addClass("default-chips")
            textContent = text
            addEventListener("click", {
                chips[type]!!.forEach {
                    it.removeClass("chips-highlight")
                }
                addClass("chips-highlight")
                choosed[type] = index
            })
            this
        }
    }

    private fun toggleDivs(name: String) {
        items[name]!!.first.classList.toggle("checkout-hide")
        items[name]!!.second.classList.toggle("checkout-hide")
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
        var name: String = nevField.value
        var email: String = emailField.value
        var phone: String = telefonszamField.value
        var zipcode: String = irszamField.value
        var city: String = telepulesField.value
        var street: String = utcaField.value
        var strnumber: String = hazszamField.value
        var payment: String = if (cash.checked) "cash" else "credit-card"

        checks.forEach {
            if (!it.value && !choosed.containsKey(it.key)) return
            if (!it.value) {
                when(it.key) {
                    "name" -> name = UserService.userInfo!!.names[choosed[it.key]!!]
                    "email" -> email = UserService.userInfo!!.emails[choosed[it.key]!!]
                    "phone" -> phone = UserService.userInfo!!.phones[choosed[it.key]!!]
                    "address" -> {
                        val obj = UserService.userInfo!!.addresses[choosed[it.key]!!]
                        zipcode = obj.zipcode.toString()
                        city = obj.city
                        street = obj.street
                        strnumber = obj.strnumber
                    }
                    else -> console.error("wtf")
                }
            }
        }

        with(Validators) {
            if (oneCharOrMore(name) && email(email)
                && phone(phone) && zipcode(zipcode)
                && oneCharOrMore(city) && oneCharOrMore(street)
                && streetnumber(strnumber)) {
                OrderService.makeOrder(name, email, phone, zipcode, city, street, strnumber, payment)
            }
        }
    }

    override fun onShow() {

    }
}