import auth.ApiRole
import auth.Auth
import globals.Endpoints
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.security.SecurityUtil.roles


fun main() {
    val path = "api"
    val app = Javalin.create().apply {
        accessManager(Auth::accessManager)
    }.contextPath(path).start(8080)

    app.routes {
        path("restaurants") {
            get(Endpoints::getRestaurants, roles(ApiRole.GUEST, ApiRole.ADMIN))
            path(":id") {
                get(Endpoints::getFoods, roles(ApiRole.GUEST))
            }
        }
        path("orders") {
            get(Endpoints::getOrders, roles(ApiRole.GUEST))
            path(":id") {
                get(Endpoints::getOrdersById, roles(ApiRole.GUEST))
            }
        }
        path("user") {
            get(Endpoints::getUser, roles(ApiRole.GUEST))
        }
        path("login") {
            post(Endpoints::login, roles(ApiRole.ANYONE))
        }
        path("insert") {
            path("restaurant") {
                post(Endpoints::insertRestaurant, roles(ApiRole.GUEST))
            }
            path("order"){
                post(Endpoints::insertOrder, roles(ApiRole.GUEST))
            }
        }
        path("registry"){
            post(Endpoints::registry, roles(ApiRole.ANYONE))
        }
    }
}