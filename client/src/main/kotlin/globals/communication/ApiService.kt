import globals.communication.HttpClient
import models.database.FoodModel
import models.database.RestaurantModel

object ApiService {
    fun getRestaurants(callback: (Array<RestaurantModel>) -> Unit) {
        HttpClient.get("restaurants") {
            val restaurants = JSON.parse<Array<RestaurantModel>>(it)
            callback.invoke(restaurants)
        }
    }
    fun getFoods(restaurantID: Int,callback: (Array<FoodModel>) -> Unit) {
        HttpClient.get("restaurants/$restaurantID") {
            val foods = JSON.parse<Array<FoodModel>>(it)
            callback.invoke(foods)
        }
    }
}