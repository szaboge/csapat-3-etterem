package globals.order

import models.database.FoodModel

object Basket {
    var basket: MutableList<FoodModel> = mutableListOf()

    fun addFood(food: FoodModel) {
        basket.add(food)
    }
}