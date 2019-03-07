package controllers

import database.DatabaseManager
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.RestaurantModel
import tornadofx.Controller

class RestaurantsController: Controller() {
    var restaurants: ObservableList<RestaurantModel> = FXCollections.observableArrayList<RestaurantModel>()

    fun getRestaurants(){
        restaurants.setAll(DatabaseManager.getRestaurants())
    }

    fun insertRestaurant(text: String) {
        DatabaseManager.insertRestaurant(text)
    }
}