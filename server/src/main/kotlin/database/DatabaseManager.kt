package database

import auth.ApiRole
import database.tables.*
import globals.format
import io.javalin.InternalServerErrorResponse
import models.communication.MakeOrderModel
import models.communication.UserByTokenModel
import models.database.FoodModel
import models.database.OrderModel
import models.database.RestaurantModel
import models.database.UserModel
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

    fun getFoods(restaurantID: Int): MutableList<FoodModel> {
        val result = mutableListOf<FoodModel>()
        transaction {
            addLogger(StdOutSqlLogger)
            result.addAll(FoodsTable.select {
                FoodsTable.restaurantID.eq(restaurantID)
            }.map {
                FoodModel(it[FoodsTable.foodsID], it[FoodsTable.restaurantID], it[FoodsTable.name], it[FoodsTable.price])
            })
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
            var sum = 0
            list.forEach {
                sum += it.price * it.count
            }
            val id = OrdersTable.insert {
                it[OrdersTable.name] = myModel.name
                it[OrdersTable.email] = myModel.email
                it[OrdersTable.phone] = myModel.phone
                it[OrdersTable.zipcode] = myModel.zipcode
                it[OrdersTable.city] = myModel.city
                it[OrdersTable.street] = myModel.street
                it[OrdersTable.strnumber] = myModel.strnumber
                it[OrdersTable.payment] = myModel.payment
                it[OrdersTable.amount] = sum
                it[OrdersTable.userID] = 1
            } get OrdersTable.orderID
            list.forEach {
                insertFood(id, it.foodID, it.count, it.price)
            }
            commit()
        }
    }

    private fun insertFood(orderID: Int?, foodID: Int, count: Int, price: Int) {
        FoodsOfOrderTable.insert {
            it[FoodsOfOrderTable.orderID] = orderID
            it[FoodsOfOrderTable.foodID] = foodID
            it[FoodsOfOrderTable.count] = count
            it[FoodsOfOrderTable.price] = price
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
                        it[OrdersTable.email],
                        it[OrdersTable.phone],
                        it[OrdersTable.zipcode],
                        it[OrdersTable.city],
                        it[OrdersTable.street],
                        it[OrdersTable.strnumber],
                        it[OrdersTable.payment],
                        it[OrdersTable.amount],
                        it[OrdersTable.userID]
                    )
                }
        }
        return result
    }

    fun getFoodsByOrder(orderID: Int?): MutableList<Pair<Int, String>> {
        val result: MutableList<Pair<Int, String>> = mutableListOf()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result.addAll((FoodsTable innerJoin FoodsOfOrderTable)
                .slice(FoodsTable.name, FoodsTable.foodsID)
                .select { FoodsTable.foodsID eq FoodsOfOrderTable.foodID }
                .map {
                    Pair(it[FoodsTable.foodsID], it[FoodsTable.name])
                })
        }
        return result
    }

    fun startGuestSession(token: String): UserByTokenModel {
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

    private fun insertGuest(): Int {
        return UsersTable.insert {
            it[role] = ApiRole.GUEST.toString()
        } get UsersTable.userID ?: throw InternalServerErrorResponse()
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

    fun getLoginUser(username: String, pw: String): MutableList<UserModel> {
        val result = mutableListOf<UserModel>()
        transaction {
            addLogger(StdOutSqlLogger)
            result.addAll(UsersTable.select { (UsersTable.email eq username) and (UsersTable.password eq pw) }
                .map {
                    UserModel(
                        it[UsersTable.userID],
                        it[UsersTable.name],
                        it[UsersTable.email],
                        it[UsersTable.password],
                        it[UsersTable.role]
                    )
                })
        }
        return result
    }

    fun startSession(token: String, userID: Int): String {
        var sessionID: String = ""
        transaction {
            sessionID = SessionsTable.insert {
                it[SessionsTable.sessionID] = token
                it[SessionsTable.userID] = userID
            } get SessionsTable.sessionID ?: throw InternalServerErrorResponse()
        }
        return sessionID
    }

    fun makeUser(name: String, email: String, password: String){
        UsersTable.insert {
            it[UsersTable.name] = name
            it[UsersTable.email] = email
            it[UsersTable.password] = password
            it[UsersTable.role] = "GUEST"
        }
    }

    fun getPriceByFoodID(): List<Pair<Int, Int>> {
        var result: List<Pair<Int, Int>> = listOf<Pair<Int, Int>>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = FoodsTable.selectAll().map {
                Pair(it[FoodsTable.foodsID].toInt(), it[FoodsTable.price].toInt())
            }
            commit()
        }
        return result
    }
}