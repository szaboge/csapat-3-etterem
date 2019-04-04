package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.textfield
import globals.ui.Routes
import org.w3c.dom.HTMLInputElement
import kotlin.dom.addClass

class CheckoutView: View() {
    override val routeType: Routes = Routes.CHECKOUT
    lateinit var input1: HTMLInputElement

    override fun render() {
        root.div {
            addClass("checkout")
            div {
                addClass("checkout-label-title")
                label("CHECKOUT YOUR ORDER") {
                    addClass("title")
                }
            }
            div{
                addClass("ck")
                input1 = textfield {
                    //Név
                    addClass("checkout-textfield ")
                    placeholder = "Név"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Email
                    addClass("checkout-textfield ")
                    placeholder = "Email"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Telefonszám
                    addClass("checkout-textfield ")
                    placeholder = "Telefonszám"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Irányítószám
                    addClass("checkout-textfield ")
                    placeholder = "Irányítószám"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Település
                    addClass("checkout-textfield ")
                    placeholder = "Település"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Utca
                    addClass("checkout-textfield ")
                    placeholder = "Utca"
                }
            }
            div {
                addClass("ck")
                textfield {
                    //Házszám
                    addClass("checkout-textfield ")
                    placeholder = "Házszám"
                }
            }
            div {
                addClass("ck")
                button("CONTINUE") {
                    addClass("checkout-continue-button")
                    addEventListener("click", {
                        println(input1.value)
                        //OrderService.makeOrder()
                    })
                }
            }
        }//div: checkout
    }

    override fun onShow() {

    }
}