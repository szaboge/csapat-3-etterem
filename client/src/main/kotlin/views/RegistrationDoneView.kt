package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.span
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import kotlin.dom.addClass

class RegistrationDoneView: View() {
    override val routeType: Routes = Routes.REGISTRATIONDONE

    override fun render() {
        with(root) {
            addClass("registration-done-view-container")
            span(Lang.getText("registration-done")) {
                addClass("registration-done-title")
            }
            button(Lang.getText("login")) {
                addClass("default-button")
                addEventListener("click", {
                    RouterService.navigate(Routes.LOGIN)
                })
            }
        }
    }

    override fun onShow() {
    }
}