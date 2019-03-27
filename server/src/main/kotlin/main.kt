import database.DatabaseManager
import globals.Endpoint
import io.javalin.Javalin
import io.javalin.core.util.Header
import java.util.*


fun main() {
    val app = Javalin.create().start(7000)

    app.get("/restaurants") {
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        it.json(DatabaseManager.getRestaurants())
    }

    app.get("/restaurants/:id") {
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        val restID = it.pathParam("id")
        it.json(DatabaseManager.getFoods(restID.toInt()))
    }

    app.post("insert/restaurant"){
        val token = it.header("token")?: "Nincs token"
        val name = it.header("name")?: ""
        DatabaseManager.insertRestaurant(name)
        it.result(token)
    }

    app.post("/order"){
        Endpoint.order(it)
    }

    app.get("/authentication") {
        try {
            val cookie: String = it.cookieStore("token")
            it.result(cookie)
        }catch (e: Exception) {
            it.status(401)
        }
    }
    app.get("/login") {
        it.cookieStore("token", UUID.randomUUID().toString())
    }

    app.get("/orders"){
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        it.json(DatabaseManager.getOrders())
    }

    app.get("/orders/:id"){
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        val oID = it.pathParam("id")
        it.json(DatabaseManager.getFoodsByOrder(oID.toInt()))
    }
}