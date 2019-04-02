package globals

import auth.Auth
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import database.DatabaseManager
import io.javalin.Context
import java.io.StringReader
import java.util.*

object Endpoints {

    fun order(ctx: Context) {
        val body = ctx.body()
        try {
            val json = Klaxon().parseJsonObject(StringReader(body))
            val array = json["foods"] as JsonArray<*>
            array.forEach { food ->
                if (food is JsonObject) {
                    println(food["name"])
                    println(food["count"])
                }
            }
            ctx.status(200)
        }catch (e: RuntimeException) {
            ctx.status(400)
        }
    }

    fun getRestaurants(ctx: Context) {
        ctx.json(DatabaseManager.getRestaurants())
    }

    fun getFoods(ctx: Context) {
        val restID = ctx.pathParam("id")
        ctx.json(DatabaseManager.getFoods(restID.toInt()))
    }

    fun getOrders(ctx: Context) {
        ctx.json(DatabaseManager.getOrders())
    }

    fun getOrdersById(ctx: Context) {
        val oID = ctx.pathParam("id")
        ctx.json(DatabaseManager.getFoodsByOrder(oID.toInt()))
    }

    fun authentication(ctx: Context) {
        Auth.authentication(ctx)
    }

    fun login(ctx: Context) {
        ctx.cookieStore("token", UUID.randomUUID().toString())
    }

    fun insertRestaurant(ctx: Context) {
        val token = ctx.header("token")?: "Nincs token"
        val name = ctx.header("name")?: ""
        DatabaseManager.insertRestaurant(name)
        ctx.result(token)
    }

}