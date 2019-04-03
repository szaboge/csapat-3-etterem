package views

import abstracts.View
import globals.ui.Routes
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import models.database.OrderModel

class OrdersView: View() {
    override val routeType: Routes = Routes.ORDERS
    var orders: Array<OrderModel> = arrayOf()
    var orderDiv = div()

    override fun render() {
        root.div {
            label("Rendelések")
            orderDiv = div {}
        }
    }

    override fun onShow() {
        ApiService.getOrders {
            orders = it
            this.generateOrders()
        }
    }

    private fun generateOrders() {
        orders.forEach {
            orderDiv.div {
                label("${it.orderID}${it.date}")
            }
        }
    }
}