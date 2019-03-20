package controllers

import database.DatabaseManager
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.OrderModel
import tornadofx.Controller

class OrdersController : Controller() {
    var orders: ObservableList<OrderModel> = FXCollections.observableArrayList<OrderModel>()

    fun getOrders() {
        orders.setAll(DatabaseManager.getOrders())
    }

}