package globals.order

import models.communication.FoodsCountModel
import models.communication.MakeOrderModel

object OrderService {
    var actualRestaurant: Int = 0

    fun makeOrder() {
        var foodsCount: MutableList<FoodsCountModel> = mutableListOf()
        Basket.basket.forEach { food ->
            foodsCount.add(FoodsCountModel(food.second.foodID, food.second.restaurantID,food.second.name,food.third))
        }
        val order = MakeOrderModel("Proba", "0620XXXXXXX", foodsCount)
        ApiService.makeOrder(order) {
            println(it.status)
        }
    }
}