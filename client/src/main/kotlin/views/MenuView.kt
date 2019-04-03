package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.span
import globals.ui.RouterService
import globals.ui.Routes
import kotlin.dom.addClass

class MenuView: View() {
    override val routeType: Routes = Routes.MENU
    override fun onShow() {}

    override fun render() {
        root.addClass("menu")
        root.button("Home") {
            addClass("menu-item")
            addEventListener("click", {
                RouterService.navigate(Routes.HOME)
            })
        }
        root.button("Restaurants") {
            addClass("menu-item")
            addEventListener("click", {
                RouterService.navigate(Routes.RESTAURANTS)
            })
        }
        root.button("Orders") {
            addClass("menu-item")
            addEventListener("click", {
                RouterService.navigate(Routes.ORDERS)
            })
        }
        root.button("Login") {
            addClass("menu-item")
            addEventListener("click", {
                RouterService.navigate(Routes.LOGIN)
            })
        }
    }
}