package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.textfield
import globals.ui.Routes
import org.w3c.dom.HTMLInputElement

class CheckoutView: View() {
    override val routeType: Routes = Routes.CHECKOUT
    lateinit var input1: HTMLInputElement

    override fun render() {
        root.div {
            input1 = textfield {

            }
            textfield {

            }
            button("Hello") {
                addEventListener("click", {
                    println(input1.value)
                    //OrderService.makeOrder()
                })
            }
        }
    }

    override fun onShow() {

    }
}