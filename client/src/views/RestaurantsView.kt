package views

import ApiService
import abstracts.View
import globals.ElementFactory.Label
import globals.Routes
import models.database.RestaurantModel

class RestaurantsView : View() {
    var restaurants: Array<RestaurantModel> = arrayOf()

    init {
        ApiService.getRestaurants {
            restaurants = it
            build()
        }
    }

    override fun render(): View {
        restaurants.forEach {
            add(Label(it.name))
        }
        return this
    }

    override val routeType = Routes.RESTAURANTS
}