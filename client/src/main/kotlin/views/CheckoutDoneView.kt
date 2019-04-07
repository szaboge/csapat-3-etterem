package views

import abstracts.View
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Routes
import kotlin.dom.addClass

class CheckoutDoneView:View() {
    override val routeType: Routes = Routes.CHECKOUTDONE

    override fun render() {
        root.div {
            addClass("checkout-done-wrapper")
            div {
                label("CHECKOUT DONE") {
                    addClass("title")
                }
            }
        }
    }

    override fun onShow() {
    }
}