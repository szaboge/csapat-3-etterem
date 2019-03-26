package views

import abstracts.View
import globals.ui.Routes

class BasketView: View() {
    override val routeType: Routes = Routes.BASKET

    override fun render(): View {
        return this
    }

    override fun onShow() {

    }
}