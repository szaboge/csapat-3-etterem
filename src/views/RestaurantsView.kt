package views

import controllers.RestaurantsController
import globals.OrderService
import globals.RouteState
import globals.RouterService
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class RestaurantsView: View() {
    private val controller: RestaurantsController by inject()
    init {
        controller.getRestaurants()
    }
    override val root = vbox{
        useMaxWidth = true
        useMaxHeight = true
        style {
            backgroundColor += Color.BISQUE
        }
        children.bind(controller.restaurants) {
            hbox {
                vboxConstraints { margin = Insets(6.0) }
                style {
                    backgroundColor += Color.ORANGE
                    backgroundRadius += box(6.px)
                    paddingAll = 6.0
                }
                flowpane {
                    alignment = Pos.CENTER_LEFT
                    label(it.name)
                }
                flowpane {
                    alignment = Pos.CENTER_RIGHT
                    button("Kiválasztás >") {
                        action {
                            OrderService.restaurantID = it.restaurantID
                            RouterService.navigate(RouteState.FOODS)
                        }
                    }
                }

            }
        }
    }
}