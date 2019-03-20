package views

import database.DatabaseManager
import globals.Basket
import javafx.scene.paint.Color
import tornadofx.*

class BasketView : View() {
    override val root = vbox {
        style {
            backgroundColor += Color.WHEAT
        }
        children.bind(Basket.foods){
            label(it.name)
        }
        button("Rendelés") {
            action {
                DatabaseManager.order()
            }
        }
    }
}