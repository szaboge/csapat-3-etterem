package views

import abstracts.View
import globals.ui.ElementFactory.Button
import globals.ui.RouterService
import globals.ui.Routes

class MenuView: View() {
    override val routeType: Routes = Routes.MENU
    override fun onShow() {}

    override fun render(): View {
        val button1 = Button("Main")
        val button2 = Button("Restaurant")
        button1.addEventListener("click", {
            RouterService.navigate(Routes.MAIN)
        })
        button2.addEventListener("click", {
            RouterService.navigate(Routes.RESTAURANTS)
        })
        add(button1)
        add(button2)
        return this
    }
}