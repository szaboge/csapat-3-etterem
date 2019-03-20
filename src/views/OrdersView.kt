package views
import controllers.OrdersController
import javafx.scene.paint.Color
import tornadofx.*

class OrdersView : View() {
    private val controller: OrdersController by inject()
    override val root = vbox{
        style {
            backgroundColor += Color.ALICEBLUE
        }
        vbox {
            useMaxWidth = true
            button("Frissítés") {
                action {
                    controller.getOrders()
                }
            }
            vbox {
                children.bind(controller.orders) {
                    label("Rendelés #${it.orderID} Dátum: ${it.date}")
                }

            }
        }
    }
}