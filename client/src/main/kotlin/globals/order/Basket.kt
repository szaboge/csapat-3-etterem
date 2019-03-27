package globals.order

import models.database.FoodModel

object Basket {
    var basket: MutableList<Triple<Int, FoodModel, Int>> = mutableListOf()

    fun addFood(food: FoodModel) {
        val filteredList = basket.filter { it.first == food.foodID }
        if (filteredList.count() > 0) {
            val oldTriple = filteredList[0]
            val newTriple = oldTriple.copy(third = oldTriple.third+1)
            basket.remove(oldTriple)
            basket.add(newTriple)
        } else {
            basket.add(Triple(food.foodID, food, 1))
        }
    }

    fun getFoods(): MutableList<Triple<Int, FoodModel, Int>> {
        basket.sortByDescending { it.third }
        return basket
    }

    fun clear() {
        basket.clear()
    }
}