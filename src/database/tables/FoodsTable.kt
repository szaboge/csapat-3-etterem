package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FoodsTable: Table("foods") {
    val foodsID: Column<Int> = RestaurantsTable.integer("restaurantID").autoIncrement().primaryKey()
    val restaurantID: Column<Int> = RestaurantsTable.integer("restaurantID")
    val name: Column<String> = RestaurantsTable.varchar("name", 30)
}