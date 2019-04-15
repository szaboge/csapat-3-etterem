package database

import auth.ApiRole
import database.tables.*
import globals.Statuses
import globals.format
import io.javalin.InternalServerErrorResponse
import models.communication.FoodsCountModel
import models.communication.GetOrderModel
import models.communication.MakeOrderModel
import models.communication.UserByTokenModel
import models.database.*
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
            } get RestaurantsTable.restaurantID ?: throw InternalServerErrorResponse()

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
                it[name] = myModel.name
                it[email] = myModel.email
                it[phone] = myModel.phone
                it[zipcode] = myModel.zipcode
                it[city] = myModel.city
                it[street] = myModel.street
                it[strnumber] = myModel.strnumber
                it[payment] = myModel.payment
                it[amount] = sum
                it[userID] = myModel.userID
                it[status] = Statuses.ARRIVING.toString()
            } get OrdersTable.orderID ?: throw InternalServerErrorResponse()
            list.forEach {
                insertFood(id, it.foodID, it.count, it.price)
            }
            commit()
        }
    }

    private fun insertFood(orderID: Int, foodID: Int, count: Int, price: Int) {
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
                        it[OrdersTable.userID],
                        it[OrdersTable.status]
                    )
                }
        }
        return result
    }

    fun insertGuest(name: String, email: String): Int {
        var id = 0
        transaction {
            id = UsersTable.insert {
                it[UsersTable.name] = name
                it[UsersTable.email] = email
                it[UsersTable.role] = ApiRole.GUEST.toString()
            } get UsersTable.userID ?: throw InternalServerErrorResponse()
            commit()
        }
        return id
    }

    fun getUserByToken(token: String): MutableList<UserByTokenModel> {
        val result = mutableListOf<UserByTokenModel>()
        transaction {
            result.addAll((SessionsTable innerJoin UsersTable)
                .slice(UsersTable.userID, UsersTable.role, SessionsTable.sessionID, UsersTable.name)
                .select { SessionsTable.sessionID eq token }
                .map {
                    UserByTokenModel(
                        it[UsersTable.userID],
                        it[UsersTable.name]!!,
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
        var sessionID = ""
        transaction {
            sessionID = SessionsTable.insert {
                it[SessionsTable.sessionID] = token
                it[SessionsTable.userID] = userID
            } get SessionsTable.sessionID ?: throw InternalServerErrorResponse()
        }
        return sessionID
    }

    fun makeUser(name: String, email: String, password: String){
        transaction {
            UsersTable.insert {
                it[UsersTable.name] = name
                it[UsersTable.email] = email
                it[UsersTable.password] = password
                it[UsersTable.role] = "USER"
            }
        }
    }

    fun getPriceByFoodID(): Map<Int, Int> {
        val result: MutableMap<Int, Int> = mutableMapOf()
            transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            FoodsTable.selectAll().map {
                result[it[FoodsTable.foodsID].toInt()] = it[FoodsTable.price].toInt()
            }
            commit()
        }
        return result
    }

    fun getOrdersByUserID(userID: Int): MutableList<GetOrderModel> {
        val result: MutableList<GetOrderModel> = mutableListOf()
        transaction {
            result.addAll(OrdersTable
                .select { OrdersTable.userID eq userID}
                .map {
                    GetOrderModel(
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
                        it[OrdersTable.userID],
                        it[OrdersTable.status],
                        getFoodsByOrder(it[OrdersTable.orderID])
                    )
                })
        }
        return result
    }

    fun getFoodsByOrder(orderID: Int): MutableList<FoodsCountModel> {
        val result: MutableList<FoodsCountModel> = mutableListOf()
        result.addAll((FoodsOfOrderTable innerJoin FoodsTable)
            .select { FoodsOfOrderTable.orderID eq orderID }
            .map {
                FoodsCountModel(
                    it[FoodsOfOrderTable.foodID],
                    it[FoodsTable.restaurantID],
                    it[FoodsTable.name],
                    it[FoodsOfOrderTable.count],
                    it[FoodsOfOrderTable.price]
                )
            })
        return result
    }

    fun updateStatus(newStatus: String, orderID: Int){
        transaction {
            addLogger(StdOutSqlLogger)
            OrdersTable.update({OrdersTable.orderID eq orderID}){
                it[OrdersTable.status] = newStatus
            }
            commit()
        }
    }
}