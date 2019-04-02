package database

import auth.ApiRole
import database.tables.*
import globals.format
import models.communication.MakeOrderModel
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
            SchemaUtils.create(RestaurantsTable, FoodsTable, OrdersTable, FoodsOfOrderTable, SessionsTable, UsersTable)
        }
    }

    fun getRestaurants(): List<RestaurantModel> {
        var res = listOf<RestaurantModel>()
        transaction {
            addLogger(StdOutSqlLogger)
            res = RestaurantsTable.selectAll()
                .map {
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
            }.map {
                FoodModel(it[FoodsTable.foodsID], it[FoodsTable.restaurantID], it[FoodsTable.name])
            }
        }
        return result
    }

    fun insertRestaurant(restaurantName: String) {
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

    fun insertOrder(myModel: MakeOrderModel) {
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            val list = myModel.foods
            val id = OrdersTable.insert {
                it[OrdersTable.name] = myModel.name
                it[OrdersTable.phone] = myModel.phone
            } get OrdersTable.orderID
            list.forEach {
                insertFood(id, it.foodID, it.count)
            }
            commit()
        }
    }

    fun insertFood(orderID: Int?, foodID: Int, count: Int) {
        FoodsOfOrderTable.insert {
            it[FoodsOfOrderTable.orderID] = orderID
            it[FoodsOfOrderTable.foodID] = foodID
            it[FoodsOfOrderTable.count] = count
        }
    }

    fun getOrders(): List<OrderModel> {

        var result = listOf<OrderModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = OrdersTable.selectAll()
                .map {
                    OrderModel(
                        it[OrdersTable.orderID],
                        it[OrdersTable.date].format(),
                        it[OrdersTable.name],
                        it[OrdersTable.phone]
                    )
                }
        }
        return result
    }

    fun getFoodsByOrder(orderID: Int?): List<Pair<Int, String>> {
        var result: List<Pair<Int, String>> = emptyList()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = (FoodsTable innerJoin FoodsOfOrderTable)
                .slice(FoodsTable.name, FoodsTable.foodsID)
                .select { FoodsTable.foodsID eq FoodsOfOrderTable.foodID }
                .map {
                    Pair(it[FoodsTable.foodsID], it[FoodsTable.name])
                }
        }
        return result
    }

    fun startSession(token: String): UserByTokenModel {
        var userID = 0
        transaction {
            userID = insertGuest()
            SessionsTable.insert {
                it[SessionsTable.sessionID] = token
                it[SessionsTable.userID] = userID
            }
        }
        return UserByTokenModel(userID, ApiRole.GUEST.toString(), token)
    }

    fun insertGuest(): Int {
        return UsersTable.insert {
            it[role] = ApiRole.GUEST.toString()
        } get UsersTable.userID ?: throw Exception()
    }

    fun getUserByToken(token: String): MutableList<UserByTokenModel> {
        val result = mutableListOf<UserByTokenModel>()
        transaction {
            result.addAll((SessionsTable innerJoin UsersTable)
                .slice(UsersTable.userID, UsersTable.role, SessionsTable.sessionID)
                .select { SessionsTable.sessionID eq token }
                .map {
                    UserByTokenModel(
                        it[UsersTable.userID],
                        it[UsersTable.role],
                        it[SessionsTable.sessionID]
                    )
                })
        }
        return result
    }

}