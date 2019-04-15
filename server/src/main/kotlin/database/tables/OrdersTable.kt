package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object OrdersTable: Table("orders") {
    val orderID: Column<Int> = integer("orderID").autoIncrement().primaryKey()
    val date: Column<DateTime> = datetime("date")
    val name: Column<String> = text("name")
    val email: Column<String> = text("email")
    val phone: Column<String> = text("phone")
    val zipcode: Column<Int> = integer("zipcode")
    val city: Column<String> = text("city")
    val street: Column<String> = text("street")
    val strnumber: Column<String> = text("strnumber")
    val payment: Column<String> = text("payment")
    val amount: Column<Int> = integer("amount")
    val userID: Column<Int> = integer("userID")
    val status: Column<String> = text("status")
}