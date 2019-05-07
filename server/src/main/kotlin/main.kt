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
                post(Endpoints::updateStatus, roles(ApiRole.KITCHEN, ApiRole.RIDER, ApiRole.ADMIN))
            }
            path("all") {
                get(Endpoints::getAllOrders, roles(ApiRole.ADMIN))
            }
            path("modifydetails") {
                post(Endpoints::modifyDetails, roles(ApiRole.ADMIN))
            }
            path("id") {
                get(Endpoints::getOneOrder, roles(ApiRole.ANYONE))
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
                post(Endpoints::insertRestaurant, roles(ApiRole.ADMIN))
            }
            path("order") {
                post(Endpoints::insertOrder, roles(ApiRole.ANYONE))
            }
            path("food"){
                post(Endpoints::addFood, roles(ApiRole.ADMIN))
            }
        }
        path("register") {
            post(Endpoints::register, roles(ApiRole.ANYONE))
        }
        path("delete") {
            path("user") {
                post(Endpoints::deleteUser, roles(ApiRole.ADMIN))
            }
            path("order"){
                post(Endpoints::deleteOrder, roles(ApiRole.ADMIN))
            }
            path("food"){
                post(Endpoints::deleteFood, roles(ApiRole.ADMIN))
            }
            path("restaurant"){
                post(Endpoints::deleteRestaurant, roles(ApiRole.ADMIN))
            }
        }
        path("user"){
            path("all"){
                get(Endpoints::getUsers, roles(ApiRole.ADMIN))
            }
            path("info"){
                get(Endpoints::getUserInfo, roles(ApiRole.USER))
            }
            path("modify"){
                post(Endpoints::modifyUserRole, roles(ApiRole.ADMIN))
            }
        }
    }
}