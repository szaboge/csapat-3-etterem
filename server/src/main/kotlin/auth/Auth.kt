package auth

import database.DatabaseManager
import io.javalin.Context
import io.javalin.Handler
import io.javalin.core.util.Header
import io.javalin.security.Role
import models.communication.UserByTokenModel
import java.util.*

enum class ApiRole: Role { ANYONE, USER, ADMIN, KITCHEN, GUEST }

object Auth {
    const val tokenKey = "token"

    fun accessManager(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
        ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        when {
            permittedRoles.contains(ApiRole.ANYONE) -> handler.handle(ctx)
            permittedRoles.contains(ApiRole.valueOf(authentication(ctx).role)) -> handler.handle(ctx)
            else -> ctx.status(403).json("Forbidden")
        }
    }

    fun authentication(ctx: Context): UserByTokenModel {
        val token = getToken(ctx)
        return if (token == "") { //no token
            DatabaseManager.startGuestSession(setToken(ctx))
        } else {
            val list = DatabaseManager.getUserByToken(token)
            if (list.count() > 0) { // have token and have user
                list.last()
            } else { // have token but not have user for token in database
                DatabaseManager.startGuestSession(setToken(ctx))
            }
        }
    }

    fun login(userID: Int, ctx: Context): String =  DatabaseManager.startSession(setToken(ctx), userID)


    private fun getToken(ctx: Context): String {
        return ctx.cookie(tokenKey)?:""
    }

    private fun setToken(ctx: Context): String {
        val token = UUID.randomUUID().toString()
        ctx.cookie(tokenKey, token)
        return token
    }

}