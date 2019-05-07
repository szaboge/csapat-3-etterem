import globals.UserService
import globals.communication.HttpClient
import models.communication.GetOrderModel
import models.communication.UserInfosModel
import models.database.FoodModel
import models.database.OrderModel
import models.database.RestaurantModel
import models.database.UserModel
import org.w3c.xhr.XMLHttpRequest

object ApiService {
    fun getRestaurants(callback: (Array<RestaurantModel>) -> Unit) {
        HttpClient.get("restaurants") {
            val restaurants = JSON.parse<Array<RestaurantModel>>(it.responseText)
            callback.invoke(restaurants)
        }
    }
    fun getFoods(restaurantID: Int,callback: (Array<FoodModel>) -> Unit) {
        HttpClient.get("restaurants/$restaurantID") {
            val foods = JSON.parse<Array<FoodModel>>(it.responseText)
            callback.invoke(foods)
        }
    }
    fun makeOrder(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("insert/order", obj) {
            callback.invoke(it)
        }
    }
    fun getOrders(callback: (Array<GetOrderModel>) -> Unit) {
        HttpClient.get("orders") {
            val orders = JSON.parse<Array<GetOrderModel>>(it.responseText)
            callback.invoke(orders)
        }
    }
    fun getAllOrders(callback: (Array<GetOrderModel>) -> Unit) {
        HttpClient.get("orders/all") {
            val orders = JSON.parse<Array<GetOrderModel>>(it.responseText)
            callback.invoke(orders)
        }
    }
    fun deleteOrder(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("delete/order", obj) {
            callback.invoke(it)
        }
    }
    fun updateState(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("orders/modifystate", obj) {
            callback.invoke(it)
        }
    }
    fun login(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("login", obj) {
            callback.invoke(it)
        }
    }
    fun authentication() {
        HttpClient.get("authentication") {
            if (it.status.toInt() == 200) {
                UserService.setUser(JSON.parse(it.responseText))
            } else {
                UserService.token = ""
            }
        }
    }
    fun getUserInfo(callback: (UserInfosModel) -> Unit) {
        HttpClient.get("user/info") {
            val item = JSON.parse<UserInfosModel>(it.responseText)
            callback.invoke(item)
        }
    }
    fun getUsers(callback: (Array<UserModel>) -> Unit) {
        HttpClient.get("user/all") {
            val item = JSON.parse<Array<UserModel>>(it.responseText)
            callback.invoke(item)
        }
    }
    fun modifyUser(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("user/modify", obj) {
            callback.invoke(it)
        }
    }
    fun deleteUser(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("delete/user", obj) {
            callback.invoke(it)
        }
    }
    fun register(obj: Any,callback: (XMLHttpRequest) -> Unit) {
        HttpClient.post("register", obj) {
            callback.invoke(it)
        }
    }

    fun getMyOrders(callback: (Array<GetOrderModel>) -> Unit) {
        HttpClient.get("orders/myorders") {
            val orders = JSON.parse<Array<GetOrderModel>>(it.responseText)
            callback.invoke(orders)
        }
    }
}