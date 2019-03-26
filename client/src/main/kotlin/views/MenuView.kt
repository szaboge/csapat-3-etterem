package views

import abstracts.View
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.RouterService
import globals.ui.Routes

class MenuView: View() {
    override val routeType: Routes = Routes.MENU
    override fun onShow() {}

    override fun render(): View {
        root.div {
            button("Home") {
                addEventListener("click", {
                    RouterService.navigate(Routes.MAIN)
                })
            }
            button("Restaurant") {
                addEventListener("click", {
                    RouterService.navigate(Routes.RESTAURANTS)
                })
            }
            button("Orders") {
                addEventListener("click", {
                    RouterService.navigate(Routes.ORDERS)
                })
            }
        }
        return this
    }
}