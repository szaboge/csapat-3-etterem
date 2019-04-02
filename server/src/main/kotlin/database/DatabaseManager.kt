package database

import auth.ApiRole
import database.tables.*
import globals.format
import models.communication.UserByTokenModel
import models.database.FoodModel
import models.database.OrderModel
import models.database.RestaurantModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseManager {
    private const val transactionLevel = Connection.TRANSACTION_READ_UNCOMMITTED

    init {
        DatabaseConnector.connect()
        TransactionManager.manager.defaultIsolationLevel = transactionLevel

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(RestaurantsTable, FoodsTable, OrdersTable, OrderFoods, SessionsTable, UsersTable)
        }
    }

    fun getRestaurants(): List<RestaurantModel> {
        var res = listOf<RestaurantModel>()
        transaction {
            addLogger(StdOutSqlLogger)
            res = RestaurantsTable.selectAll()
                .map{
                    RestaurantModel(it[RestaurantsTable.restaurantID], it[RestaurantsTable.name])
                }
        }
        return res
    }

    fun getFoods(restaurantID: Int): List<FoodModel> {
        var result = listOf<FoodModel>()
        transaction {
            addLogger(StdOutSqlLogger)
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

        var result = listOf<OrderModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = OrdersTable.selectAll()
                .map{
                    OrderModel(it[OrdersTable.orderID], it[OrdersTable.date].format())
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

    fun startSession(token: String) {
        transaction {
            val userID: Int = insertGuest()
            SessionsTable.insert {
                it[SessionsTable.sessionID] = token
                it[SessionsTable.userID] = userID
            }
        }
    }

    private fun insertGuest(): Int {
        return UsersTable.insert {
            it[role] = ApiRole.GUEST.toString()
        } get UsersTable.userID ?: throw Exception()
    }

    fun getUserByToken(token: String): MutableList<UserByTokenModel> {
        var result = mutableListOf<UserByTokenModel>()
        transaction {
            result.addAll((SessionsTable innerJoin UsersTable)
                .slice(UsersTable.userID, UsersTable.role, SessionsTable.expiration)
                .select { SessionsTable.sessionID eq token }
                .map { UserByTokenModel(it[UsersTable.userID], it[UsersTable.role], it[SessionsTable.expiration]) })
        }
        return result
    }
}