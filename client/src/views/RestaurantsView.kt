package views

import ApiService
import abstracts.View
import globals.OrderService
import globals.ui.ElementFactory.button
import globals.ui.ElementFactory.div
import globals.ui.ElementFactory.label
import globals.ui.RouterService
import globals.ui.Routes
import models.database.RestaurantModel
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

class RestaurantsView : View() {
    var restaurants: Array<RestaurantModel> = arrayOf()
    var destination = document.createElement("div") as HTMLDivElement

    override fun onShow() {
        ApiService.getRestaurants {
            restaurants = it
            build()
        }
    }


    override fun render(): View {
        destination = root.div {
            restaurants.forEach {
                div {
                    label(it.name)
                    button("Kiválasztás") {
                        addEventListener("click", { _ -> openRestaurant(it.restaurantID) })
                    }
                }
            }
        }
        return this
    }

    fun openRestaurant(id: Int){
        OrderService.actualRestaurant = id
        RouterService.navigate(Routes.FOODS)
    }


    override val routeType = Routes.RESTAURANTS
}