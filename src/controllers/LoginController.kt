package controllers

import database.DatabaseConnector
import database.tables.Restaurants
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller
import java.sql.Connection

class LoginController : Controller() {
    fun insert() {
        DatabaseConnector.connect()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_READ_COMMITTED
        transaction {
            addLogger(StdOutSqlLogger)
            Restaurants.insert {
                it[name] = "Valami"
            }
            commit()
        }
    }
}