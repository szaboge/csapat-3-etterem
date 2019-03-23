package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object RestaurantsTable: Table("restaurants") {
    val restaurantID: Column<Int> = integer("restaurantID").autoIncrement().primaryKey()
    val name: Column<String> = text("name")
}