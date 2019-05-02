import auth.ApiRole
import auth.Auth
import globals.Endpoints
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.security.SecurityUtil.roles
import org.eclipse.jetty.io.EndPoint


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
            get(Endpoints::getOrders, roles(ApiRole.KITCHEN, ApiRole.RIDER))
            path("myorders") {
                get(Endpoints::getMyOrders, roles(ApiRole.USER))
            }
            path("modifystate") {
                post(Endpoints::updateStatus, roles(ApiRole.KITCHEN, ApiRole.RIDER))
            }
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
            path("order") {
                post(Endpoints::insertOrder, roles(ApiRole.ANYONE))
            }
        }
        path("register") {
            post(Endpoints::register, roles(ApiRole.ANYONE))
        }
        path("delete") {
            path("user") {
                delete(Endpoints::deleteUser, roles(ApiRole.ADMIN))
            }
        }
        path("user"){
            get(Endpoints::getUsers, roles(ApiRole.ANYONE))//Admin
            path("info"){
                get(Endpoints::getUserInfo, roles(ApiRole.ANYONE))//User
            }
        }
    }
}