package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UsersTable: Table("users") {
    val userID: Column<Int> = integer("userID").autoIncrement().primaryKey().uniqueIndex()
    val name: Column<String> = varchar("name", 40)
    val email: Column<String> = varchar("email", 40)
    val password: Column<String?> = varchar("password", 64).nullable()
    val role: Column<String> = varchar("role",30)
    val restaurantID: Column<Int> = integer("restaurantID").references(RestaurantsTable.restaurantID)
}