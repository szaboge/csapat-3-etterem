package views

import abstracts.View
import globals.ElementFactory.Button
import globals.RouterService
import globals.Routes

class MenuView: View() {
    override val routeType: Routes = Routes.MENU

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