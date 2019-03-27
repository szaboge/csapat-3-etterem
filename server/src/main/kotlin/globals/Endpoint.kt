package globals

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import io.javalin.Context
import io.javalin.core.util.Header
import java.io.StringReader

object Endpoint {

    fun order(ctx: Context) {
        ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        val body = ctx.body()
        try {
            val array = Klaxon().parseJsonObject(StringReader(body))["foods"] as JsonArray<*>
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
}