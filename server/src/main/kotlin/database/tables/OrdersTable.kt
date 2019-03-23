package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object OrdersTable: Table("orders") {
    val orderID: Column<Int> = integer("orderID").autoIncrement().primaryKey()
    val date: Column<DateTime> = datetime("date")
}