package globals

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.FoodsModel

object Basket {
    var foods: ObservableList<FoodsModel> = FXCollections.observableArrayList<FoodsModel>()

    fun addFood(food:FoodsModel) {
        foods.add(food)
    }
    fun clear() {
        foods.clear()
    }
}