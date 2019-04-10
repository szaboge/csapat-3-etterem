package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import kotlin.dom.addClass

class HomeView: View() {
    override val routeType = Routes.HOME

    override fun onShow() {
    }

    override fun render() {
        with(root) {
            addClass("home")
            button(Lang.getText("home-title")) {
                addClass("default-button get-started")
                addEventListener("click", {
                    RouterService.navigate(Routes.RESTAURANTS)
                })
            }
        }
    }
}
