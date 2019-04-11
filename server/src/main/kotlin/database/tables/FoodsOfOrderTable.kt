package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FoodsOfOrderTable: Table("foods_of_one_order") {
    val orderID: Column<Int> = integer("orderID").references(OrdersTable.orderID)
    val foodID: Column<Int> = integer("foodID").references(FoodsTable.foodsID)
    val orderFoodID: Column<Int> = integer("orderFoodID").autoIncrement().primaryKey()
    val count: Column<Int> = integer("count")
    val price: Column<Int> = integer("price")
}