package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FoodsTable: Table("foods") {
    val foodsID: Column<Int> = integer("foodID").autoIncrement().primaryKey()
    val restaurantID: Column<Int?> = integer("restaurantID").nullable()
    val name: Column<String> = text("name")
}