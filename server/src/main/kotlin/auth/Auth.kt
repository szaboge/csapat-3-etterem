package auth

import io.javalin.Context
import io.javalin.Handler
import io.javalin.core.util.Header
import io.javalin.security.Role

enum class ApiRole : Role { ANYONE, USER, ADMIN, KITCHEN }

object Auth {
    fun accessManager(handler: Handler, ctx: Context, permittedRoles: Set<Role>) {
        ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        if (permittedRoles.contains(ApiRole.ANYONE)) {
            handler.handle(ctx)
        } else {
            ctx.status(403).result("Forbidden - 403")
        }
    }

}