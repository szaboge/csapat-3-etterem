package views

import ApiService
import abstracts.View
import globals.order.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.RouterService
import globals.ui.Routes
import models.database.RestaurantModel
import org.w3c.dom.HTMLDivElement

class RestaurantsView : View() {
    override val routeType = Routes.RESTAURANTS
    var restaurants: Array<RestaurantModel> = arrayOf()
    var destination: HTMLDivElement = div()

    override fun onShow() {
        ApiService.getRestaurants {
            restaurants = it
            generateRestaurant()
        }
    }

    override fun render(): View {
        destination = root.div {
        }
        return this
    }

    private fun generateRestaurant() {
        restaurants.forEach {
            destination.div {
                label(it.name)
                button("Kiválasztás") {
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