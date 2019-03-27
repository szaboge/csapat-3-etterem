import globals.communication.HttpClient
import models.database.FoodModel
import models.database.OrderModel
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
    fun makeOrder(obj: Any,callback: (String) -> Unit) {
        println(JSON.stringify(obj))
        HttpClient.post("makeorder", obj) {
            callback.invoke(it)
        }
    }
    fun getOrders(callback: (Array<OrderModel>) -> Unit) {
        HttpClient.get("orders") {
            val orders = JSON.parse<Array<OrderModel>>(it)
            callback.invoke(orders)
        }
    }
}