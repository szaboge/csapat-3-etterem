package globals

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.FoodModel

object Basket {
    var foods: ObservableList<FoodModel> = FXCollections.observableArrayList<FoodModel>()

    fun addFood(food:FoodModel) {
        foods.add(food)
    }
    fun clear() {
        foods.clear()
    }
}