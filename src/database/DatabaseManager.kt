package database

import database.tables.FoodsTable
import database.tables.OrderFoods
import database.tables.OrdersTable
import database.tables.RestaurantsTable
import globals.Basket
import models.FoodModel
import models.OrderModel
import models.RestaurantModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseManager {
    private const val transactionLevel = Connection.TRANSACTION_READ_COMMITTED

    init {
        DatabaseConnector.connect()
        TransactionManager.manager.defaultIsolationLevel = transactionLevel
    }

    fun getRestaurants(): List<RestaurantModel> {
        var res = listOf<RestaurantModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            res = RestaurantsTable.selectAll()
                .map{
                    RestaurantModel(it[RestaurantsTable.restaurantID],it[RestaurantsTable.name])
                }
        }
        return res
    }

    fun getFoods(restaurantID: Int): List<FoodModel> {
        var result = listOf<FoodModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = FoodsTable.select {
                FoodsTable.restaurantID.eq(restaurantID)
            }.map{
                FoodModel(it[FoodsTable.foodsID], it[FoodsTable.restaurantID], it[FoodsTable.name])
            }
        }
        return result
    }

    fun insertRestaurant(restaurantName: String){
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            val id = RestaurantsTable.insert {
                it[name] = restaurantName
            } get RestaurantsTable.restaurantID

            FoodsTable.insert {
                it[restaurantID] = id
                it[name] = "Valami kaja"
            }
            commit()
        }
    }

    fun order() {
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            val id = OrdersTable.insert {
            } get OrdersTable.orderID

            Basket.foods.forEach {
                insertFood(id, it.foodID)
            }

            commit()
        }
    }

    private fun insertFood(orderID: Int?, foodID: Int){
        OrderFoods.insert {
            it[OrderFoods.orderID] = orderID
            it[OrderFoods.foodID] = foodID
        }
    }

    fun getOrders(): List<OrderModel> {
        var result = listOf<OrderModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = OrdersTable.selectAll()
                .map{
                    OrderModel(it[OrdersTable.orderID],it[OrdersTable.date])
                }
        }
        println(result.count())
        return result
    }
}