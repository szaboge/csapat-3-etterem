package views

import ApiService
import abstracts.View
import globals.order.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.Lang
import globals.ui.RouterService
import globals.ui.Routes
import models.database.RestaurantModel
import org.w3c.dom.HTMLDivElement
import kotlin.dom.addClass

class RestaurantsView : View() {
    override val routeType = Routes.RESTAURANTS
    var restaurants: Array<RestaurantModel> = arrayOf()

    override fun onShow() {
        ApiService.getRestaurants {
            restaurants = it
            generateRestaurant()
        }
    }

    override fun render(){
        root.addClass("restaurants-root")
    }

    private fun generateRestaurant() {
        restaurants.forEach {
            root.div {
                addClass("restaurants-item")
                label(it.name)
                button(Lang.getText("restaurants-select")) {
                    addClass("default-button")
                    addEventListener("click", { _ -> openRestaurant(it.restaurantID) })
                }
            }
        }
    }

    private fun openRestaurant(id: Int){
        OrderService.actualRestaurant = id
        RouterService.navigate(Routes.FOODS)
    }
}