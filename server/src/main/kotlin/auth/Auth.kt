package auth

import database.DatabaseManager
import globals.format
import io.javalin.Context
import io.javalin.Handler
import io.javalin.core.util.Header
import io.javalin.security.Role
import models.communication.UserByTokenModel
import java.util.*

enum class ApiRole(val value: String): Role { ANYONE("anyone"), USER("user"), ADMIN("admin"), KITCHEN("kitchen"), GUEST("guest") }

object Auth {
    val tokenKey = "token"

    fun accessManager(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
        ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        when {
            permittedRoles.contains(ApiRole.ANYONE) -> handler.handle(ctx)
            else -> ctx.status(401).json("Unauthorized")
        }
    }

    fun authentication(ctx: Context) {
        val token = getToken(ctx)
        if (token == "") { // nincs tokenja > uj session
            DatabaseManager.startSession(startSession(ctx))
        } else {
            val result = DatabaseManager.getUserByToken(token)
            if (result.count() > 0) {

            } else { // van tokenja de nincs az adatbazisban
                DatabaseManager.startSession(startSession(ctx))
            }
        }
    }

    private fun getToken(ctx: Context): String {
        return ctx.cookie(tokenKey)?:""
    }

    private fun startSession(ctx: Context): String {
        val token = UUID.randomUUID().toString()
        ctx.cookie(tokenKey, token)
        return token
    }

}