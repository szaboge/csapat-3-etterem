package views

import abstracts.View
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.img
import globals.ui.ElementFactory.label
import globals.ui.Routes
import kotlin.dom.addClass

class HomeView: View() {
    override val routeType = Routes.HOME

    override fun onShow() {
    }

    override fun render(): View {
        root.addClass("home")
        root.img("img/home.jpg") {
            addClass("home-image")
        }
        return this
    }
}
