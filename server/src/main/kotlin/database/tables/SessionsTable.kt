package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object SessionsTable: Table("sessions") {
    val sessionID: Column<String> = text("sessionID").primaryKey()
    val userID: Column<Int> = integer("userID").references(UsersTable.userID)
    var expiration: Column<DateTime> = datetime("expiration")
}