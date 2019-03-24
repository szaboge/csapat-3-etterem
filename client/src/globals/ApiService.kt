import globals.HttpClient
import models.database.RestaurantModel

object ApiService {
    fun getRestaurants(callback: (Array<RestaurantModel>) -> Unit) {
        HttpClient.get("restaurants") {
            val restaurants = JSON.parse<Array<RestaurantModel>>(it)
            callback.invoke(restaurants)
        }
    }
}