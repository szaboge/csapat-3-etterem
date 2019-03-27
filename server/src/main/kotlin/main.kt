import com.beust.klaxon.Klaxon
import database.DatabaseManager
import io.javalin.Javalin
import io.javalin.core.util.Header
import models.FoodModel


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

    app.post("/makeorder"){
        it.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        val body = Klaxon().parse<Array<FoodModel>>(it.body())


        /*val result: Array<FoodModel>
        va*r i: Int = 0
        for(a in it.body()){
            result[i] = Klaxon().parse<FoodModel>(a)
            i.inc()
        }*/

        when (body) {
            null -> it.status(400)
            else -> DatabaseManager.order(body)
        }
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