package database

import models.FoodModel
import models.OrderFoodsModel
import models.OrderModel
import models.RestaurantModel
import database.tables.FoodsTable
import database.tables.OrderFoods
import database.tables.OrdersTable
import database.tables.RestaurantsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import org.joda.time.format.DateTimeFormat



object DatabaseManager {
    private const val transactionLevel = Connection.TRANSACTION_READ_UNCOMMITTED

    init {
        DatabaseConnector.connect()
        TransactionManager.manager.defaultIsolationLevel = transactionLevel

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(RestaurantsTable, FoodsTable, OrdersTable, OrderFoods)
        }
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

    fun order(list: Array<FoodModel>) {
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            val id = OrdersTable.insert {
            } get OrdersTable.orderID
            list.forEach {
                //insertFood(id, it.foodID)
            }
            commit()
        }
    }

    private fun insertFood(orderID: Int?, foodID: Int, count: Int){
        OrderFoods.insert {
            it[OrderFoods.orderID] = orderID
            it[OrderFoods.foodID] = foodID
            it[OrderFoods.count] = count
        }
    }

    fun getOrders(): List<OrderModel> {
        val fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
        var result = listOf<OrderModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = OrdersTable.selectAll()
                .map{
                    OrderModel(it[OrdersTable.orderID],fmt.print(it[OrdersTable.date]))
                }
        }
        return result
    }

    fun getFoodsByOrder(orderID: Int?): List<Pair<Int, String>> {
        var result:  List<Pair<Int, String>> = emptyList()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = (FoodsTable innerJoin OrderFoods)
                .slice(FoodsTable.name, FoodsTable.foodsID)
                .select{ FoodsTable.foodsID eq OrderFoods.foodID}
                .map {
                    Pair(it[FoodsTable.foodsID], it[FoodsTable.name])
                }
            /*result = OrderFoods.select {
                OrderFoods.orderID.eq(orderID)
            }.map{
                OrderFoodsModel(it[OrderFoods.orderID], it[OrderFoods.foodID], it[OrderFoods.orderFoodID], it[OrderFoods.count])
            }*/
        }
        return result
    }
}