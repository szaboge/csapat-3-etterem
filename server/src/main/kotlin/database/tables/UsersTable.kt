package database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UsersTable: Table("users") {
    val userID: Column<Int> = integer("userID").autoIncrement().primaryKey()
    val name: Column<String?> = varchar("name", 40).nullable()
    val email: Column<String?> = varchar("email", 40).nullable()
    val password: Column<String?> = varchar("password", 64).nullable()
    val role: Column<String> = varchar("role",30)
}