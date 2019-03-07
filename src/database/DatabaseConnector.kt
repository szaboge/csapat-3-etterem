package database

import org.jetbrains.exposed.sql.Database

object DatabaseConnector {
    fun connect() {
        println("Connected")
        Database.connect("jdbc:mysql://www.remotemysql.com:3306/zjyZ4sU4UK?useSSL=false", driver = "com.mysql.jdbc.Driver",
            user = "zjyZ4sU4UK", password = "aRY12udoI9")
    }
}

/*
Username: zjyZ4sU4UK
Password: aRY12udoI9
Database Name: zjyZ4sU4UK
Server: remotemysql.com
Port: 3306
 */