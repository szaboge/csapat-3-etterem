import database.DatabaseManager
import io.javalin.Javalin
import io.javalin.core.util.Header

fun main() {
    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    app.error(404) { ctx ->
        ctx.result("Nincs ilyen oldal")
    }

    app.error(403) { ctx ->
        ctx.result("Nincs hozzá jogod")
    }

    app.get("/restaurants") {
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        it.json(DatabaseManager.getRestaurants())
    }

    app.get("/restaurants/:id"){
        it.status(403)
        it.result("A  id: "+it.pathParam("id"))
    }

    app.post("insert/user"){
        val token = it.header("token")?: "Nincs token"
        val username = it.header("username")?:""
        val password = it.header("password")?:""
        it.result(token)
    }

    app.post("insert/food"){
        val token = it.header("token")?: "Nincs token"
        val restaurantID = it.header("token")?: ""
        val name = it.header("name")?: ""
        it.result(token)
    }

    app.post("insert/restaurant"){
        val token = it.header("token")?: "Nincs token"
        val name = it.header("name")?: ""
        DatabaseManager.insertRestaurant(name)
        it.result(token)
    }

    app.get("/foods/:id") {
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        val restID = it.pathParam("id")
        it.json(DatabaseManager.getFoods(restID.toInt()))
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