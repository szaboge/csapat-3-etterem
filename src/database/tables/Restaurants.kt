package database.tables


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Restaurants: Table("restaurants") {
    val id: Column<Int> = integer("restaurantID").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 30)
}