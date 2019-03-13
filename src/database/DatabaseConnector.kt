package database

import org.jetbrains.exposed.sql.Database

object DatabaseConnector {
    private val user: String = "zjyZ4sU4UK"
    private val password: String = "aRY12udoI9"
    private val url: String = "www.remotemysql.com:3306/$user?useSSL=false"
    fun connect() {
        Database.connect("jdbc:mysql://$url", driver = "com.mysql.jdbc.Driver",
            user = user, password = password)
    }
}

/*
Username: zjyZ4sU4UK
Password: aRY12udoI9
Database Name: zjyZ4sU4UK
Server: remotemysql.com
Port: 3306
 */