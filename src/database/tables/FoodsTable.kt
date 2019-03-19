package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FoodsTable: Table("foods") {
    val foodsID: Column<Int> = FoodsTable.integer("restaurantID").autoIncrement().primaryKey()
    val restaurantID: Column<Int?> = FoodsTable.integer("restaurantID").nullable()
    val name: Column<String> = FoodsTable.text("name")
}