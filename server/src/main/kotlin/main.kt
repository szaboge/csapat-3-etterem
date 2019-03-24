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

    app.post("insertuser"){
        val token = it.header("token")?: "Nincs token"
        val username = it.header("username")?:""
        val password = it.header("password")?:""
        it.result(token)
    }

    app.get("/foods") {
            ctx ->  ctx.result("Jejjj")
    }
}