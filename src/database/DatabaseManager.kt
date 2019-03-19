package database

import database.tables.FoodsTable
import database.tables.RestaurantsTable
import models.FoodsModel
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

    fun getFoods(restaurantID: Int): List<FoodsModel> {
        var result = listOf<FoodsModel>()
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            result = FoodsTable.select {
                FoodsTable.restaurantID.eq(restaurantID)
            }.map{
                FoodsModel(it[FoodsTable.foodsID],it[FoodsTable.restaurantID],it[FoodsTable.name])
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
}