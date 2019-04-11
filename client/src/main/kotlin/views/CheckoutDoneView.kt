package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.ElementFactory.span
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import kotlin.dom.addClass

class CheckoutDoneView:View() {
    override val routeType: Routes = Routes.CHECKOUTDONE

    override fun render() {
        with(root) {
            addClass("checkout-done-view-container")
            span(Lang.getText("checkout-done")) {
                addClass("checkout-done-title")
            }
            button("RENDELÉSEIM") {
                addClass("default-button")
                addEventListener("click", {
                    RouterService.navigate(Routes.MYORDERS)
                })
            }
        }
    }

    override fun onShow() {
    }
}