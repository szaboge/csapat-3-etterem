package globals.order

import globals.ui.RouterService
import globals.ui.Routes
import models.communication.FoodsCountModel
import models.communication.MakeOrderModel

object OrderService {
    var actualRestaurant: Int = 0
    fun makeOrder(name: String, email: String, phone: String, zipcode: String, city:String, street: String, strnumber:String, payment:String) {
        var foodsCount: MutableList<FoodsCountModel> = mutableListOf()
        Basket.basket.forEach { food ->
            foodsCount.add(FoodsCountModel(food.second.foodID, food.second.restaurantID,food.second.name,food.third,food.second.price))
        }
        val order = MakeOrderModel(name, email,phone,zipcode,city,street,strnumber, payment, foodsCount)

         ApiService.makeOrder(order) {
             if (it.status.toInt() == 200) {
                RouterService.navigate(Routes.CHECKOUTDONE)
            }
        }
    }
}