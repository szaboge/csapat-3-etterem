package database

import database.tables.RestaurantsTable
import models.RestaurantModel
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
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
            res = RestaurantsTable.selectAll()
                .map{
                    RestaurantModel(it[RestaurantsTable.restaurantID],it[RestaurantsTable.name])
                }
        }
        return res
    }

    fun insertRestaurant(restaurantName: String){
        transaction {
            addLogger(StdOutSqlLogger) // log SQL query
            RestaurantsTable.insert {
                it[name] = restaurantName
            }
            commit()
        }
    }
}