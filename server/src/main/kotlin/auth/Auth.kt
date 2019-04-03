package auth

import database.DatabaseManager
import io.javalin.Context
import io.javalin.ForbiddenResponse
import io.javalin.Handler
import io.javalin.core.util.Header
import io.javalin.security.Role
import models.communication.UserByTokenModel
import java.util.*

enum class ApiRole: Role { ANYONE, USER, ADMIN, KITCHEN, GUEST, RIDER }

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
        println(token)
        if (token == "") {
            throw ForbiddenResponse()
        } else {
            val list = DatabaseManager.getUserByToken(token)
            if (list.count() > 0) {
                return list.last()
            } else {
                throw ForbiddenResponse()
            }
        }
    }

    fun login(userID: Int, ctx: Context): String =  DatabaseManager.startSession(genToken(), userID)


    fun getToken(ctx: Context): String = ctx.header(tokenKey)?: ""
    fun genToken(): String = UUID.randomUUID().toString()

}