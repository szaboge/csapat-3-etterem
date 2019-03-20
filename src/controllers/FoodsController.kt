package controllers

import database.DatabaseManager
import globals.OrderService
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.FoodModel
import tornadofx.Controller

class FoodsController: Controller() {
    var foods: ObservableList<FoodModel> = FXCollections.observableArrayList<FoodModel>()
    var restaurantID: Int = OrderService.restaurantID

    fun getFoods() {
        restaurantID = OrderService.restaurantID
        foods.setAll(DatabaseManager.getFoods(restaurantID))
    }

}