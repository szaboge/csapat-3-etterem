package database

import org.jetbrains.exposed.sql.Database


object DatabaseConnector {
    fun connect() {
        Database.connect("jdbc:sqlite:src/main/kotlin/database/resource/db.db", "org.sqlite.JDBC")
    }
}