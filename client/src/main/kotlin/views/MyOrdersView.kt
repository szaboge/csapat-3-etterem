package views

import abstracts.View
import globals.ui.ElementFactory.label
import globals.ui.Routes

class MyOrdersView: View() {
    override val routeType: Routes = Routes.MYORDERS

    override fun render() {
        with(root) {
            label("MyOrders")
        }
    }

    override fun onShow() {

    }
}