package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FoodsOfOrderTable: Table("foods_of_one_order") {
    val orderID: Column<Int?> = integer("orderID").nullable()
    val foodID: Column<Int> = integer("foodID")
    val orderFoodID: Column<Int> = integer("orderFoodID").autoIncrement().primaryKey()
    val count: Column<Int> = integer("count")
}