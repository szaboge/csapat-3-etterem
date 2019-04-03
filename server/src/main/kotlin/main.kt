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
    }.contextPath(path).enableCorsForOrigin("*").start(8080)

    app.routes {
        path("restaurants") {
            get(Endpoints::getRestaurants, roles(ApiRole.ANYONE))
            path(":id") {
                get(Endpoints::getFoods, roles(ApiRole.ANYONE))
            }
        }
        path("orders") {
            get(Endpoints::getOrders, roles(ApiRole.ANYONE))
            path(":id") {
                get(Endpoints::getOrdersById, roles(ApiRole.ANYONE))
            }
        }
        path("user") {
            get(Endpoints::getUser, roles(ApiRole.ANYONE))
        }
        path("login") {
            post(Endpoints::login, roles(ApiRole.ANYONE))
        }
        path("authentication") {
            get(Endpoints::authentication, roles(ApiRole.ANYONE))
        }
        path("insert") {
            path("restaurant") {
                post(Endpoints::insertRestaurant, roles(ApiRole.ANYONE))
            }
            path("order"){
                post(Endpoints::insertOrder, roles(ApiRole.ANYONE))
            }
        }
        path("registry"){
            post(Endpoints::registry, roles(ApiRole.ANYONE))
        }
    }
}